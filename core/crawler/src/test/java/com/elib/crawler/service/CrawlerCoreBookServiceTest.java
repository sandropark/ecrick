package com.elib.crawler.service;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CrawlerCoreBookServiceTest {

    @Autowired CrawlerCoreService crawlerCoreService;
    @Autowired CoreRepository coreRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

    // 코어 2 각각 다른 책 -> 북 2 저장 및 각각 연관관계 저장
    @DisplayName("Core의 데이터로 Book을 생성해서 저장 후 연관관계를 맺는다.")
    @Test
    void insertBooksFromCoreAndSetBookId1() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스").author("하라리").publisher("김영사")
                .publicDate(LocalDate.of(2022, 1, 1)).coverUrl("링크1").build();
        Core core2 = Core.builder().title("총균쇠").build();

        coreRepository.saveAllAndFlush(List.of(core1, core2));

        // When
        crawlerCoreService.insertBooksFromCoreAndSetBookId();

        em.clear();

        // Then
        List<Book> books = bookRepository.findAll();
        List<Core> cores = coreRepository.findAll();
        assertThat(books).hasSize(2);

        Book 사피엔스 = books.get(0);
        Book 총균쇠 = books.get(1);

        Core findCore1 = cores.get(0);
        Core findCore2 = cores.get(1);

        assertThat(findCore1.getBook()).isEqualTo(사피엔스);
        assertThat(findCore2.getBook()).isEqualTo(총균쇠);
    }

    // 코어 2 같은 책 다른 출간일 -> 북 1 (출간일 최신) 저장 및 같은 연관관계 저장
    @DisplayName("core의 (제목,저자,출판사)가 같은데 출간일이 다른 경우, 출간일 최신인 Book만 저장 후 연관관계를 맺는다.")
    @Test
    void insertBooksFromCoreAndSetBookId2() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스")
                .publicDate(LocalDate.of(2022, 1, 1)).build();
        Core core2 = Core.builder().title("사피엔스").build();

        coreRepository.saveAllAndFlush(List.of(core1, core2));

        // When
        crawlerCoreService.insertBooksFromCoreAndSetBookId();

        em.clear();

        // Then
        List<Book> books = bookRepository.findAll();
        List<Core> cores = coreRepository.findAll();
        assertThat(books).hasSize(1);

        Book 사피엔스 = books.get(0);

        Core findCore1 = cores.get(0);
        Core findCore2 = cores.get(1);

        assertThat(findCore1.getBook()).isEqualTo(사피엔스);
        assertThat(findCore2.getBook()).isEqualTo(사피엔스);
    }



    @DisplayName("Book에 데이터가 있고, Core에 데이터가 추가된 경우 : ")
    @Test
    void insertBooksFromCoreAndSetBookId3() throws Exception {
        // Given
        Core core1 = Core.builder().title("사피엔스").publicDate(LocalDate.of(1999,1,1)).build();
        coreRepository.saveAndFlush(core1);

        crawlerCoreService.insertBooksFromCoreAndSetBookId();

        Core core2 = Core.builder().title("사피엔스").build();  // Book에 있지만 출간일이 최신이 아닌 데이터
        LocalDate latestPublicDate = LocalDate.of(2023, 1, 1);
        Core core3 = Core.builder().title("사피엔스").publicDate(latestPublicDate)
                .build();   // Book에 있는데 출간일이 최신인 데이터
        Core core4 = Core.builder().title("토지").build();  // Book에 없는 데이터

        coreRepository.saveAllAndFlush(List.of(core2, core3, core4));

        // When
        crawlerCoreService.insertBooksFromCoreAndSetBookId();

        em.flush();
        em.clear();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);

        Book 사피엔스 = books.get(0);

        assertThat(사피엔스.getPublicDate()).isEqualTo(latestPublicDate);   // 최신 출간일로 수정됨

        List<Core> cores = coreRepository.findAll();
        List<Core> sapiens = cores.subList(0, 3);  // 사피엔스만 뽑기
        for (Core core : sapiens) {
            assertThat(core.getBook()).isEqualTo(사피엔스); // 모두 같은 연관관계를 가지고 있다.
        }

        Core core = cores.get(3);
        Book 토지 = books.get(1);
        assertThat(core.getBook()).isEqualTo(토지);
    }

}