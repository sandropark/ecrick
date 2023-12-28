//package com.ecrick.crawler.infrastructure;
//
//import com.ecrick.crawler.CrawlerTestSupport;
//import com.ecrick.crawler.infrastructure.RowBookRepositoryImpl;
//import com.ecrick.entity.Book;
//import com.ecrick.entity.RowBook;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class JpaRowBookRepositoryImplTest extends CrawlerTestSupport {
//
//    @Autowired
//    RowBookRepositoryImpl rowBookRepositoryImpl;
//
//    @DisplayName("제목,저자,출판사 모두 같은 core와 book끼리 연관관계를 맺는다.")
//    @Test
//    void mapCoreAndBookIfCore_BookIdIsNull() throws Exception {
//        // Given
//        String title = "토지";
//        String author = "박경리";
//        String publisher = "김영사";
//        RowBook rowBook = saveCore(title, author, publisher);
//        Book book = saveBook(title, author, publisher);
//
//        assertThat(rowBook.getBook()).isNull();
//
//        // When
//        rowBookRepositoryImpl.mapCoreAndBookIfCore_BookIdIsNull();
//        em.clear();
//
//        // Then
//        RowBook findRowBook = jpaRowBookRepository.findById(rowBook.getId()).get();
//        assertThat(findRowBook.getBook()).isEqualTo(book);
//    }
//
//    @DisplayName("제목,저자,출판사 중 하나라도 맞지 않다면 연관관계를 맺지 않는다.")
//    @Test
//    void mapCoreAndBookIfCore_BookIdIsNull2() throws Exception {
//        // Given
//        String title = "토지";
//        String author = "박경리";
//        RowBook rowBook = saveCore(title, author, "김영사");
//        saveBook(title, author, "출판사 김영사");
//
//        assertThat(rowBook.getBook()).isNull();
//
//        // When
//        rowBookRepositoryImpl.mapCoreAndBookIfCore_BookIdIsNull();
//        em.clear();
//
//        // Then
//        RowBook findRowBook = jpaRowBookRepository.findById(rowBook.getId()).get();
//        assertThat(findRowBook.getBook()).isNull();
//    }
//
//    private Book saveBook(String title, String author, String publisher) {
//        return jpaBookRepository.saveAndFlush(Book.builder().title(title).author(author).publisher(publisher).build());
//    }
//
//    private RowBook saveCore(String title, String author, String publisher) {
//        return jpaRowBookRepository.saveAndFlush(RowBook.builder().title(title).author(author).publisher(publisher).build());
//    }
//
//}