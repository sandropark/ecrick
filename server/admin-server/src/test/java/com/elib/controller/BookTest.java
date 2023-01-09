package com.elib.controller;

import com.elib.domain.Book;
import com.elib.domain.CoreBook;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreBookRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookTest {

    @Autowired BookRepository bookRepository;
    @Autowired CoreBookRepository coreBookRepository;

    @Disabled
    @DisplayName("실제 DB에서 Book 가져오는 테스트")
    @Test
    void book() throws Exception {
        List<Book> all = bookRepository.findAll();
        System.out.println("all.size() = " + all.size());
    }

    @Disabled
    @DisplayName("실제 DB에서 coreBook 가져오는 테스트")
    @Test
    void coreBook() throws Exception {
        List<CoreBook> all = coreBookRepository.findAll();
        System.out.println("all.size() = " + all.size());
    }
}
