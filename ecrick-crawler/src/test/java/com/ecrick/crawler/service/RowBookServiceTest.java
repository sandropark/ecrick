//package com.ecrick.crawler.service;
//
//import com.ecrick.crawler.CrawlerTestSupport;
//import com.ecrick.entity.Book;
//import com.ecrick.entity.RowBook;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class RowBookServiceTest extends CrawlerTestSupport {
//
//    @Autowired RowBookService rowBookService;
//
//    @DisplayName("Core의 데이터로 Book을 생성해서 저장 후 연관관계를 맺는다.")
//    @Test
//    void insertBooksFromCoreAndSetBookId1() throws Exception {
//        // Given
//        RowBook rowBook1 = RowBook.builder().title("사피엔스").build();
//        RowBook rowBook2 = RowBook.builder().title("토지").build();
//
//        // When
//        saveCoresAndCreateBook(List.of(rowBook1, rowBook2));
//        em.clear();
//
//        // Then
//        List<RowBook> rowBooks = jpaRowBookRepository.findAll();
//        List<Book> books = jpaBookRepository.findAll();
//        assertThat(rowBooks).hasSize(2);
//        assertThat(books).hasSize(2);
//
//        Book 사피엔스 = book_사피엔스(books);
//        Book 토지 = book_토지(books);
//        RowBook findRowBook1 = rowBooks.get(0);
//        RowBook findRowBook2 = rowBooks.get(1);
//
//        assertThat(findRowBook1.getBook()).isEqualTo(사피엔스);
//        assertThat(findRowBook2.getBook()).isEqualTo(토지);
//    }
//
//    @DisplayName("core의 (제목,저자,출판사)가 같은데 출간일이 다른 경우, 출간일 최신인 Book만 저장 후 연관관계를 맺는다.")
//    @Test
//    void insertBooksFromCoreAndSetBookId2() throws Exception {
//        // Given
//        RowBook rowBook1 = RowBook.builder().title("사피엔스").publicDate(publicDate(2000)).build();
//        RowBook rowBook2 = RowBook.builder().title("사피엔스").publicDate(publicDate(2022)).build();
//
//        // When
//        saveCoresAndCreateBook(List.of(rowBook1, rowBook2));
//        em.clear();
//
//        // Then
//        List<RowBook> rowBooks = jpaRowBookRepository.findAll();
//        List<Book> books = jpaBookRepository.findAll();
//        assertThat(rowBooks).hasSize(2);
//        assertThat(books).hasSize(1);
//
//        Book 사피엔스 = book_사피엔스(books);
//        assertThat(사피엔스.getPublicDate()).isEqualTo(publicDate(2022));
//        assertThat(rowBooks.get(0).getBook()).isEqualTo(사피엔스);
//        assertThat(rowBooks.get(1).getBook()).isEqualTo(사피엔스);
//    }
//
//    @DisplayName("Book에 데이터가 있고, Core에 데이터가 추가된 경우 : ")
//    @Test
//    void insertBooksFromCoreAndSetBookId3() throws Exception {
//        // Given
//        RowBook rowBook1 = RowBook.builder().title("사피엔스").publicDate(publicDate(1999)).build();
//        RowBook rowBook2 = RowBook.builder().title("사피엔스").publicDate(publicDate(2023)).build();
//        RowBook rowBook3 = RowBook.builder().title("사피엔스").publicDate(publicDate(2000)).build();
//        RowBook rowBook4 = RowBook.builder().title("토지").build();
//        saveCoreAndCreateBook(rowBook1);
//
//        // When
//        saveCoresAndCreateBook(List.of(rowBook2, rowBook3, rowBook4));
//        em.clear();
//
//        // Then
//        List<Book> books = jpaBookRepository.findAll();
//        List<RowBook> rowBooks = jpaRowBookRepository.findAll();
//
//        assertThat(books).hasSize(2);
//        assertThat(book_사피엔스(books).getPublicDate()).isEqualTo(publicDate(2023));   // 최신 출간일로 수정됨
//
//        for (RowBook 사피엔스 : cores_사피엔스(rowBooks))
//            assertThat(사피엔스.getBook()).isEqualTo(book_사피엔스(books)); // 모두 같은 연관관계를 가지고 있다.
//
//        assertThat(core_토지(rowBooks).getBook()).isEqualTo(book_토지(books));
//    }
//
//    private Book book_사피엔스(List<Book> books) {
//        return books.get(0);
//    }
//
//    private Book book_토지(List<Book> books) {
//        return books.get(1);
//    }
//
//    private List<RowBook> cores_사피엔스(List<RowBook> rowBooks) {
//        return rowBooks.subList(0, 3);
//    }
//
//    private RowBook core_토지(List<RowBook> rowBooks) {
//        return rowBooks.get(3);
//    }
//
//    private void saveCoresAndCreateBook(List<RowBook> rowBooks) {
//        jpaRowBookRepository.saveAllAndFlush(rowBooks);
//        rowBookService.insertBooksFromCoreAndSetBookId();
//    }
//
//    private void saveCoreAndCreateBook(RowBook rowBook1) {
//        jpaRowBookRepository.saveAndFlush(rowBook1);
//        rowBookService.insertBooksFromCoreAndSetBookId();
//    }
//
//    private LocalDate publicDate(int year) {
//        return LocalDate.of(year, 1, 1);
//    }
//
//}