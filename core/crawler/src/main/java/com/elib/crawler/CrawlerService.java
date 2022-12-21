package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.repository.EbookServiceRepository;
import com.elib.repository.LibraryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        EbookService service = ebookServiceRepository.findByName("교보");
        log.info("{}", library.getName());
        ResponseDto responseDto = getResponseDto(library);
        if (responseDto != null) {
            updateLibraryTotalBooks(library, responseDto);
            List<String> detailUrls = responseDto.getDetailUrl(library.getApiUrl());
            crawl(detailUrls, library, service);
        }
    }

    private ResponseDto getResponseDto(Library library) {
        try {
            return CrawlerUtil.responseToDto(CrawlerUtil.requestUrl(library.getApiUrl()));
        } catch (JAXBException | JsonProcessingException e) {
            log.error("CrawlerService 파싱 오류 library = {}", library.getName(), e);
            return null;
        } catch (IOException e) {
            log.error("{} error", library.getName(), e);
            return null;
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
