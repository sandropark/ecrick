package com.elib.crawler.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcTemplateCoreRepositoryTest {

    @Autowired CoreRepository coreRepository;
    @Autowired BookRepository bookRepository;
    @Autowired JdbcTemplateCoreRepository jdbcTemplateCoreRepository;
    @Autowired EntityManager em;

    @DisplayName("core데이터로 book에서 매칭되는 데이터를 찾아서 연관관계를 맺는다.")
    @Test
    void updateBookIdAll() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        String publisher = "김영사";
        LocalDate publicDate = LocalDate.of(2018, 1, 1);
        String coverUrl = "naver.com";

        Core core = Core.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publicDate(publicDate)
                .coverUrl(coverUrl)
                .build();
        coreRepository.saveAndFlush(core);

        Book book = Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publicDate(publicDate)
                .coverUrl(coverUrl)
                .build();
        bookRepository.saveAndFlush(book);

        // When
        jdbcTemplateCoreRepository.updateBookIdAll();

        em.clear();

        // Then
        Core findCore = coreRepository.findById(core.getId()).get();
        Book findBook = bookRepository.findById(book.getId()).get();
        assertThat(findCore.getBook().getTitle()).isEqualTo(findBook.getTitle());
    }

}