package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.CoreBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
class CoreBookRepositoryTest {

    @Autowired CoreBookRepository coreBookRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

    @DisplayName("제목,저자,출판사로 group by해서 중복이 있는 데이터를 coreBook으로 반환한다.")
    @Test
    void findAllDuplicateDate() throws Exception {
        // Given
        Book book1 = Book.builder().title("사피엔스").author("하라리").publisher("김영사")
                .publicDate(LocalDate.of(2016, 1, 1)).build();

        Book book2 = Book.builder().title("사피엔스").author("하라리").publisher("김영사")
                .publicDate(LocalDate.of(1, 1, 1)).build();

        Book book3 = Book.builder().title("토지").author("박경리").publisher("김영사")
                .publicDate(LocalDate.of(1, 1, 1)).build();

        Book book4 = Book.builder().title("토지").author("박경리").publisher("김영사")
                .publicDate(LocalDate.of(2011, 1, 1)).build();

        Book book5 = Book.builder().title("총균쇠").author("다이아몬드").publisher("김영사")
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
        List<CoreBook> duplicateCoreBooks = coreBookRepository.findAllDuplicateDate();

        //Then
        assertThat(duplicateCoreBooks).hasSize(4);

    }

}