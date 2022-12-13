package sandro.elib.elib.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.crwaler.dto.JsonDto;
import sandro.elib.elib.crwaler.dto.ResponseDto;
import sandro.elib.elib.domain.EbookService;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.repository.EbookServiceRepository;
import sandro.elib.elib.repository.LibraryRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sandro.elib.elib.crwaler.CrawlUtil.*;

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

        JsonDto JsonDto;
        try {
            JsonDto = getJsonDto(requestUrlAndGetResponse(library));
        } catch (IOException e) {
            return;
        }
        List<String> detailUrls = getDetailUrls(JsonDto.getTotalBooks(), library.getApiUrl());

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
            responseDto = getJsonDto(requestUrlAndGetResponse(library));
        } catch (IOException e) {
            return;
        }
        List<String> detailUrls = getDetailUrls(responseDto.getTotalBooks(), library.getApiUrl());

        execute(library, detailUrls);
    }

    private void execute(Library library, List<String> detailUrls) {
        ExecutorService es = Executors.newFixedThreadPool(40);     // 스레드 풀
        EbookService service = ebookServiceRepository.findByName("교보"); // TODO : 도서관 서비스 확장시 수정
        detailUrls.forEach(url -> {
            Crawler crawler = crawlerProvider.getObject();
            crawler.init(url, library, service);
            es.submit(crawler);   // task를 입력
        });
    }

}
