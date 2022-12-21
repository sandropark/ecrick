package com.elib.crawler;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import com.elib.dto.BookDto;
import com.elib.service.BookService;
import com.elib.service.RelationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Scope("prototype")
@Component
public class Crawler implements Runnable {

    private final BookService bookService;
    private final RelationService relationService;
    private String detailUrl;
    private Library library;
    private EbookService service;
    private int sleepTime;

    public void init(String detailUrl, Library library, EbookService service, int sleepTime) {
        this.detailUrl = detailUrl;
        this.library = library;
        this.service = service;
        this.sleepTime = sleepTime * 1000;
    }

    @Override
    public void run() {
        log.info("{} 쓰레드 작업 시작", this.library.getName());
        sleep();
        List<BookDto> bookDtos = this.getBookDtos();
        if (bookDtos != null) {
            this.save(bookDtos);
        }

        log.info("{} 작업 완료 쓰레드 종료", this.library.getName());
    }

    private List<BookDto> getBookDtos() {
        try {
            return CrawlerUtil.responseToDto(CrawlerUtil.requestUrl(detailUrl)).toBookDto();
        } catch (JAXBException | JsonProcessingException e) {
            log.error("파싱 예외 발생 쓰레드 종료 url = {}", detailUrl, e);
            return null;
        } catch (IOException e) {
            log.error("예외 발생 쓰레드 종료 url = {}", detailUrl, e);
            return null;
        }
    }

    private void save(List<BookDto> bookDtos) {
        bookDtos.forEach((bookDto) -> {
            Book book = bookService.saveBookDto(bookDto);
            relationService.saveNotExists(Relation.of(book, library, service));
        });
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException var2) {
            throw new RuntimeException(var2);
        }
    }
}
