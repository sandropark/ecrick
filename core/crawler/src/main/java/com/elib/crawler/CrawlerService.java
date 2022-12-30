package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.repository.EbookServiceRepository;
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

    private final EbookServiceRepository ebookServiceRepository;
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
        Library library = libraryRepository.findById(libraryId).orElseThrow(() ->
            new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
        EbookService service = ebookServiceRepository.findByName(library.getService());
        log.info("{}", library.getName());
        ResponseDto responseDto = getResponseDto(library.getUrl());
        if (responseDto != null) {
            updateLibraryTotalBooks(library, responseDto);
            List<String> detailUrls = responseDto.getDetailUrl(library.getUrl());
            crawl(detailUrls, library, service);
        }
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

    private void crawl(List<String> detailUrls, Library library, EbookService service) {
        ExecutorService es = Executors.newFixedThreadPool(threadNum);
        detailUrls.forEach((url) -> {
            Crawler crawler = crawlerProvider.getObject();
            crawler.init(url, library, service, sleepTime);
            es.submit(crawler);
        });
    }

}
