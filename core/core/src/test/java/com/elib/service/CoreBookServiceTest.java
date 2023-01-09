package com.elib.service;

import com.elib.domain.Book;
import com.elib.domain.CoreBook;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CoreBookServiceTest {

    @Autowired CoreBookRepository coreBookRepository;
    @Autowired BookRepository bookRepository;
    @Autowired CoreBookService coreBookService;
    @Autowired EntityManager em;

    @DisplayName("출간일만 다른 연관관계를 들고있는 데이터를 모두 가져와서 최신 출간일 데이터로 연관관계를 모두 수정 후 나머지 데이터는 삭제한다.")
    @Test
    void reduceDuplicateFromPublicDate() throws Exception {
        // Given
        String 사피엔스 = "사피엔스";
        String 하라리 = "하라리";
        String 김영사 = "김영사";
        Book book1 = Book.builder().title(사피엔스).author(하라리).publisher(김영사)
                .publicDate(LocalDate.of(2016, 1, 1)).build();

        Book book2 = Book.builder().title(사피엔스).author(하라리).publisher(김영사)
                .publicDate(LocalDate.of(1, 1, 1)).build();

        String 토지 = "토지";
        String 박경리 = "박경리";
        Book book3 = Book.builder().title(토지).author(박경리).publisher(김영사)
                .publicDate(LocalDate.of(1, 1, 1)).build();

        Book book4 = Book.builder().title(토지).author(박경리).publisher(김영사)
                .publicDate(LocalDate.of(2011, 1, 1)).build();

        Book book5 = Book.builder().title("총균쇠").author("다이아몬드").publisher(김영사)
                .publicDate(LocalDate.of(1, 1, 1)).build();


        bookRepository.saveAllAndFlush(List.of(book1, book2, book3, book4, book5));

        CoreBook coreBook1 = CoreBook.builder().coreId(1L).book(book1).build();
        CoreBook coreBook2 = CoreBook.builder().coreId(2L).book(book2).build();
        CoreBook coreBook3 = CoreBook.builder().coreId(3L).book(book3).build();
        CoreBook coreBook4 = CoreBook.builder().coreId(4L).book(book4).build();
        CoreBook coreBook5 = CoreBook.builder().coreId(5L).book(book5).build();

        coreBookRepository.saveAllAndFlush(List.of(coreBook1, coreBook2, coreBook3, coreBook4, coreBook5));

        em.clear();

        // When
        coreBookService.reduceDuplicateFromPublicDate();

        em.flush();
        em.clear();

        // Then
        List<CoreBook> coreBooks1 = coreBookRepository.findByTitleAndAuthorAndPublisher(사피엔스, 하라리, 김영사);
        assertThat(coreBooks1.get(0).getBook()).isEqualTo(book1);
        assertThat(coreBooks1.get(1).getBook()).isEqualTo(book1);

        List<CoreBook> coreBooks2 = coreBookRepository.findByTitleAndAuthorAndPublisher(토지, 박경리, 김영사);
        assertThat(coreBooks2.get(0).getBook()).isEqualTo(book4);
        assertThat(coreBooks2.get(1).getBook()).isEqualTo(book4);


        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(3);
    }

}