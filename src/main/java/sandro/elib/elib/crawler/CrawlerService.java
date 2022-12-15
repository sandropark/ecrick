package sandro.elib.elib.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.crawler.dto.ResponseDto;
import sandro.elib.elib.domain.EbookService;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.repository.EbookServiceRepository;
import sandro.elib.elib.repository.LibraryRepository;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sandro.elib.elib.crawler.CrawlUtil.requestUrl;
import static sandro.elib.elib.crawler.CrawlUtil.responseToDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerService implements Runnable {

    private final EbookServiceRepository ebookServiceRepository;
    private final LibraryRepository libraryRepository;
    private final ObjectProvider<Crawler> crawlerProvider;
    private Long libraryId;
    private boolean singleCrawler;

    public void init(Long libraryId, boolean singleCrawler) {
        this.libraryId = libraryId;
        this.singleCrawler = singleCrawler;
    }

    @Override
    public void run() {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
        EbookService service = ebookServiceRepository.findByName("교보"); // TODO : 도서관 서비스 확장시 수정

        log.info("{}", library.getName());

        ResponseDto responseDto;
        try {
            responseDto = responseToDto(requestUrl(library.getApiUrl()));
        } catch (JsonProcessingException | JAXBException e) {
            log.error("CrawlerService 파싱 오류 library = {}",library.getName(), e);
            return;
        } catch (IOException e) {
            log.error("error", e);
            return;
        }
        updateLibraryTotalBooks(library, responseDto);

        List<String> detailUrls = responseDto.getDetailUrl(library.getApiUrl());

        if (singleCrawler) {
            singleCrawl(detailUrls, library, service);
        } else {
            MultiCrawl(detailUrls, library, service);
        }
    }

    @Transactional
    private void updateLibraryTotalBooks(Library library, ResponseDto responseDto) {
        library.updateTotalBooks(responseDto.getTotalBooks());
        libraryRepository.save(library);
    }

    private void singleCrawl(List<String> detailUrls, Library library, EbookService service) {
        Crawler crawler = crawlerProvider.getObject();
        detailUrls.forEach(url -> {
            crawler.init(url, library, service, false);
            crawler.run();
        });
    }

    private void MultiCrawl(List<String> detailUrls, Library library, EbookService service) {
        ExecutorService es = Executors.newFixedThreadPool(50);     // 스레드 풀
        detailUrls.forEach(url -> {
            Crawler crawler = crawlerProvider.getObject();
            crawler.init(url, library, service);
            es.submit(crawler);   // task를 입력
        });
    }

}