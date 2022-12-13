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

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sandro.elib.elib.crawler.CrawlUtil.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerService {

    private final EbookServiceRepository ebookServiceRepository;
    private final LibraryRepository libraryRepository;
    private final ObjectProvider<Crawler> crawlerProvider;

    @Transactional
    public void crawl(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElse(null);
        if (library == null) {
            return;
        }

        log.info("CrawlerService 작업 시작 library = {}", library.getName());

        ResponseDto responseDto;
        try {
            responseDto = responseToDto(requestUrlAndGetResponse(library));
        } catch (JsonProcessingException | JAXBException e) {
            log.error("파싱 오류", e);
            return;
        } catch (IOException e) {
            log.error("error", e);
            return;
        }
        List<String> detailUrls = getDetailUrls(responseDto, library.getApiUrl());

        execute(library, detailUrls);
    }

    @Transactional
    public void crawl(String libraryName) {
        Library library = libraryRepository.findByName(libraryName)
                .orElse(null);
        if (library == null) {
            return;
        }

        ResponseDto responseDto;
        try {
            responseDto = responseToDto(requestUrlAndGetResponse(library));
        } catch (IOException | JAXBException e) {
            return;
        }
        List<String> detailUrls = getDetailUrls(responseDto, library.getApiUrl());

        execute(library, detailUrls);
    }

    private void execute(Library library, List<String> detailUrls) {
        ExecutorService es = Executors.newFixedThreadPool(50);     // 스레드 풀
        EbookService service = ebookServiceRepository.findByName("교보"); // TODO : 도서관 서비스 확장시 수정
        detailUrls.forEach(url -> {
            Crawler crawler = crawlerProvider.getObject();
            crawler.init(url, library, service);
            es.submit(crawler);   // task를 입력
        });
    }

}
