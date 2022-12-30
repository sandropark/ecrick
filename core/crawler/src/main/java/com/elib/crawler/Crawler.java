package com.elib.crawler;

import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import com.elib.dto.BookDto;
import com.elib.service.BookService;
import com.elib.service.RelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.elib.crawler.CrawlerUtil.getResponseDto;

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
        log.info("{} 쓰레드 작업 시작", library.getName());
        sleep();

        ResponseDto responseDto = getResponseDto(detailUrl);

        if (responseDto != null) {
            save(responseDto.toBookDto());
        }

        log.info("{} 작업 완료 쓰레드 종료", library.getName());
    }

    private void save(List<BookDto> bookDtos) {
        bookDtos.forEach(bookDto -> {
            Book book = bookService.saveBookDto(bookDto);   // DB에 해당 도서가 없으면 저장
            relationService.saveNotExists(Relation.of(book, library, service)); // DB에 해당 관계가 없으면 저장
        });
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
