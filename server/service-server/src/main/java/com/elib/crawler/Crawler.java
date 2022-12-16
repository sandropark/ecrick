package com.elib.crawler;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import com.elib.dto.BookDto;
import com.elib.repository.BookRepository;
import com.elib.repository.RelationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

@Slf4j
@RequiredArgsConstructor
@Scope("prototype")
@Component
public class Crawler implements Runnable {

    private final BookRepository bookRepository;
    private final RelationRepository relationRepository;
    private String detailUrl;
    private Library library;
    private EbookService service;
    private boolean sleep;

    public void init(String detailUrl, Library library, EbookService service) {
        init(detailUrl, library, service, true);
    }

    public void init(String detailUrl, Library library, EbookService service, boolean sleep) {
        this.detailUrl = detailUrl;
        this.library = library;
        this.service = service;
        this.sleep = sleep;
    }

    @Override
    public void run() {
        if (sleep) {
            sleepRandomTime();
        }

        log.info("{} 쓰레드 작업 시작", library.getName());
        List<BookDto> bookDtos;

        try {
            bookDtos = CrawlUtil.responseToDto(CrawlUtil.requestUrl(detailUrl)).toBookDto();
        } catch (JsonProcessingException | JAXBException e) {
            log.error("파싱 예외 발생 쓰레드 종료 url = {}", detailUrl, e);
            return;
        } catch (IOException e) {
            log.error("예외 발생 쓰레드 종료 url = {}", detailUrl, e);
            return;
        }

        save(bookDtos);
        log.info("{} 작업 완료 쓰레드 종료", library.getName());
    }


    @Transactional
    private void save(List<BookDto> bookDtos) { // TODO : 책 저장하는 로직 조금 더 깔끔하게 손보기
        bookDtos.forEach(bookDto -> {
            Book book = bookRepository.findByDto(bookDto);
            if (book == null) {
                book = bookDto.toEntity();
                bookRepository.save(book);
            } else if (book.hasNotPublicDate() && bookDto.hasPublicDate()) {
                book.updatePublicDate(bookDto.getPublicDate());
            }
            Relation relation = Relation.of(book, library, service);
            if (relationRepository.notExist(relation)) {
                relationRepository.save(relation);
            }
        });
    }

    private static void sleepRandomTime() {
        Random random = new Random();
        try {
            sleep(abs(random.nextInt(30000)) + 5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
