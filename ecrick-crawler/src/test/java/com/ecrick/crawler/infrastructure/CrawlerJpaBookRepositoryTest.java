//package com.ecrick.crawler.infrastructure;
//
//import com.ecrick.crawler.CrawlerTestSupport;
//import com.ecrick.crawler.service.port.BookRepository;
//import com.ecrick.entity.Book;
//import com.ecrick.entity.RowBook;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class CrawlerJpaBookRepositoryTest extends CrawlerTestSupport {
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @DisplayName("insertBookFromCore : Core의 데이터로 Book에 저장한다.")
//    @Test
//    void insertBookFromCore1() throws Exception {
//        // Given
//        String title = "토지";
//        String author = "박경리";
//        String publisher = "김영사";
//        LocalDate publicDate = publicDate(2018);
//        String coverUrl = "naver.com";
//
//        saveCore(title, author, publisher, publicDate, coverUrl);
//
//        // When
//        bookRepository.insertBookFromCore();
//
//        // Then
//        List<Book> books = jpaBookRepository.findAll();
//        assertThat(books).hasSize(1);
//
//        Book book = books.get(0);
//        assertThat(book.getTitle()).isEqualTo(title);
//        assertThat(book.getAuthor()).isEqualTo(author);
//        assertThat(book.getPublisher()).isEqualTo(publisher);
//        assertThat(book.getPublicDate()).isEqualTo(publicDate);
//        assertThat(book.getCoverUrl()).isEqualTo(coverUrl);
//    }
//
//    @DisplayName("insertBookFromCore : 중복된 데이터는 출간일이 최신인 데이터만 저장한다.")
//    @Test
//    void insertBookFromCore2() throws Exception {
//        // Given
//        RowBook rowBook1 = RowBook.builder().title("토지").publicDate(publicDate(2012)).build();
//        RowBook rowBook2 = RowBook.builder().title("토지").publicDate(publicDate(2000)).build();
//        jpaRowBookRepository.saveAllAndFlush(List.of(rowBook1, rowBook2));
//
//        // When
//        bookRepository.insertBookFromCore();
//
//        // Then
//        List<Book> books = jpaBookRepository.findAll();
//        assertThat(books).hasSize(1);
//
//        assertThat(books.get(0).getPublicDate()).isEqualTo(publicDate(2012));
//    }
//
//    @DisplayName("insertBookFromCore : Book에 저장된 데이터와 제목,저자,출판사가 같다면 저장되지 않는다.")
//    @Test
//    void insertBookFromCore3() throws Exception {
//        // Given
//        String title = "토지";
//        String author = "박경리";
//        String publisher = "김영사";
//        saveBook(title, author, publisher);
//        saveCore(title, author, publisher);
//
//        // When
//        bookRepository.insertBookFromCore();
//
//        // Then
//        Assertions.assertThat(jpaBookRepository.findAll()).hasSize(1);
//    }
//
//    @DisplayName("insertBookFromCore : Book에 저장된 데이터와 제목,저자,출판사가 같다면 출간일이 최신으로 업데이트된다.")
//    @Test
//    void insertBookFromCore4() throws Exception {
//        // Given
//        String title = "토지";
//        String author = "박경리";
//        String publisher = "김영사";
//        saveBook(title, author, publisher, publicDate(1999));
//        saveCore(title, author, publisher, publicDate(2022));
//        em.clear();
//
//        // When
//        bookRepository.insertBookFromCore();
//
//        // Then
//        Assertions.assertThat(jpaBookRepository.findAll()).hasSize(1);
//        Assertions.assertThat(jpaBookRepository.findAll().get(0).getPublicDate()).isEqualTo(publicDate(2022));
//    }
//
//    private LocalDate publicDate(int year) {
//        return LocalDate.of(year, 1, 1);
//    }
//
//    private void saveCore(String title, String author, String publisher, LocalDate publicDate, String coverUrl) {
//        jpaRowBookRepository.saveAndFlush(
//                RowBook.builder().title(title)
//                        .author(author)
//                        .publisher(publisher)
//                        .publicDate(publicDate)
//                        .coverUrl(coverUrl)
//                        .build()
//        );
//    }
//
//    private void saveCore(String title, String author, String publisher) {
//        jpaRowBookRepository.saveAndFlush(RowBook.builder().title(title).author(author).publisher(publisher).build());
//    }
//
//    private void saveCore(String title, String author, String publisher, LocalDate newDate) {
//        RowBook rowBook = RowBook.builder().title(title).author(author).publisher(publisher).publicDate(newDate).build();
//        jpaRowBookRepository.saveAndFlush(rowBook);
//    }
//
//    private void saveBook(String title, String author, String publisher) {
//        jpaBookRepository.saveAndFlush(Book.builder().title(title).author(author).publisher(publisher).build());
//    }
//
//    private void saveBook(String title, String author, String publisher, LocalDate pubDate) {
//        jpaBookRepository.saveAndFlush(Book.builder().title(title).author(author).publisher(publisher).publicDate(pubDate).build());
//    }
//
//}