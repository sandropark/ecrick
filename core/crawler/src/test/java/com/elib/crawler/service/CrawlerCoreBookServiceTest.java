package com.elib.crawler.service;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CrawlerCoreBookServiceTest {

    @Autowired CrawlerCoreService crawlerCoreService;
    @Autowired CoreRepository coreRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

    @DisplayName("Core의 데이터로 Book을 생성해서 저장 후 연관관계를 맺는다.")
    @Test
    void insertBooksFromCoreAndSetBookId1() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스").build();
        Core core2 = Core.builder().title("토지").build();

        // When
        saveCoresAndCreateBook(List.of(core1, core2));
        em.clear();

        // Then
        List<Core> cores = coreRepository.findAll();
        List<Book> books = bookRepository.findAll();
        assertThat(cores).hasSize(2);
        assertThat(books).hasSize(2);

        Book 사피엔스 = book_사피엔스(books);
        Book 토지 = book_토지(books);
        Core findCore1 = cores.get(0);
        Core findCore2 = cores.get(1);

        assertThat(findCore1.getBook()).isEqualTo(사피엔스);
        assertThat(findCore2.getBook()).isEqualTo(토지);
    }

    @DisplayName("core의 (제목,저자,출판사)가 같은데 출간일이 다른 경우, 출간일 최신인 Book만 저장 후 연관관계를 맺는다.")
    @Test
    void insertBooksFromCoreAndSetBookId2() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스").publicDate(publicDate(2000)).build();
        Core core2 = Core.builder().title("사피엔스").publicDate(publicDate(2022)).build();

        // When
        saveCoresAndCreateBook(List.of(core1, core2));
        em.clear();

        // Then
        List<Core> cores = coreRepository.findAll();
        List<Book> books = bookRepository.findAll();
        assertThat(cores).hasSize(2);
        assertThat(books).hasSize(1);

        Book 사피엔스 = book_사피엔스(books);
        assertThat(사피엔스.getPublicDate()).isEqualTo(publicDate(2022));
        assertThat(cores.get(0).getBook()).isEqualTo(사피엔스);
        assertThat(cores.get(1).getBook()).isEqualTo(사피엔스);
    }

    @DisplayName("Book에 데이터가 있고, Core에 데이터가 추가된 경우 : ")
    @Test
    void insertBooksFromCoreAndSetBookId3() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스").publicDate(publicDate(1999)).build();
        Core core2 = Core.builder().title("사피엔스").publicDate(publicDate(2023)).build();
        Core core3 = Core.builder().title("사피엔스").publicDate(publicDate(2000)).build();
        Core core4 = Core.builder().title("토지").build();
        saveCoreAndCreateBook(core1);

        // When
        saveCoresAndCreateBook(List.of(core2, core3, core4));
        em.clear();

        // Then
        List<Book> books = bookRepository.findAll();
        List<Core> cores = coreRepository.findAll();

        assertThat(books).hasSize(2);
        assertThat(book_사피엔스(books).getPublicDate()).isEqualTo(publicDate(2023));   // 최신 출간일로 수정됨

        for (Core 사피엔스 : cores_사피엔스(cores))
            assertThat(사피엔스.getBook()).isEqualTo(book_사피엔스(books)); // 모두 같은 연관관계를 가지고 있다.

        assertThat(core_토지(cores).getBook()).isEqualTo(book_토지(books));
    }

    private Book book_사피엔스(List<Book> books) {
        return books.get(0);
    }

    private Book book_토지(List<Book> books) {
        return books.get(1);
    }

    private List<Core> cores_사피엔스(List<Core> cores) {
        return cores.subList(0, 3);
    }

    private Core core_토지(List<Core> cores) {
        return cores.get(3);
    }

    private void saveCoresAndCreateBook(List<Core> cores) {
        coreRepository.saveAllAndFlush(cores);
        crawlerCoreService.insertBooksFromCoreAndSetBookId();
    }

    private void saveCoreAndCreateBook(Core core1) {
        coreRepository.saveAndFlush(core1);
        crawlerCoreService.insertBooksFromCoreAndSetBookId();
    }

    private LocalDate publicDate(int year) {
        return LocalDate.of(year, 1, 1);
    }

}