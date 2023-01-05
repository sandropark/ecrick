package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;
import com.elib.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.elib.crawler.CrawlerUtil.getResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerService implements Runnable {

    private final LibraryRepository libraryRepository;
    private final ObjectProvider<Crawler> crawlerProvider;
    private Long libraryId;
    private int threadNum;
    private int sleepTime;

    public void init(Long libraryId, int threadNum, int sleepTime) {
        this.libraryId = libraryId;
        this.threadNum = threadNum;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        Library library = libraryRepository.findById(libraryId) // TODO : DTO 사용하기
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));

        log.info("{} 크롤링 시작", library.getName());

        getResponseDto(library.getUrl(), library).ifPresent(responseDto -> {
            updateLibraryTotalBooks(library, responseDto);
            crawl(library.getDetailUrls(), library);
        });
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

    private void crawl(List<String> detailUrls, Library library) {
        ExecutorService es = Executors.newFixedThreadPool(threadNum);
        detailUrls.forEach(url -> {
            Crawler crawler = crawlerProvider.getObject();
            crawler.init(url, library, sleepTime);
            es.submit(crawler);
        });
    }

}
