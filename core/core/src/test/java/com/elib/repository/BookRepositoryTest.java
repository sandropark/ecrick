package com.elib.repository;

import com.elib.domain.Book;
import com.elib.dto.BookListDto;
import com.elib.dto.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static com.elib.service.SearchTarget.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.save(Book.builder().title("사피엔스").author("하라리").publisher("김영사").publicDate(LocalDate.of(2004, 1, 1)).build());
        bookRepository.save(Book.builder().title("토지 1").author("박완서").publisher("인사이트").publicDate(LocalDate.of(2001, 1, 1)).build());
        bookRepository.save(Book.builder().title("토지 2").author("박완서").publisher("인사이트").publicDate(LocalDate.of(2002, 1, 1)).build());
        bookRepository.save(Book.builder().title("총균쇠").author("제러드 다이아몬드").publisher("김영사").publicDate(LocalDate.of(2003, 1, 1)).build());
    }

    @DisplayName("검색어를 입력하지 않으면 모든 데이터가 조회된다.")
    @Test
    void searchPage() throws Exception {
        Page<BookListDto> page = bookRepository.searchPage(new Search(), PageRequest.ofSize(20));

        assertThat(page.getContent()).hasSize(4);
    }

    @DisplayName("제목을 타겟으로 검색어를 입력하면 제목에 검색어가 포함된 책 목록을 반환한다.")
    @Test
    void searchPageWithTitleKeyword() throws Exception {
        String keyword = "토지";
        Page<BookListDto> page = bookRepository.searchPage(new Search(TITLE, keyword), PageRequest.ofSize(20));
        List<BookListDto> content = page.getContent();

        assertThat(content).hasSize(2);
        assertThat(content.get(0).getTitle()).contains(keyword);
        assertThat(content.get(1).getTitle()).contains(keyword);
    }

    @DisplayName("저자를 타겟으로 검색어를 입력하면 저자에 검색어가 포함된 책 목록을 반환한다.")
    @Test
    void searchPageWithAuthorKeyword() throws Exception {
        String keyword = "박완서";
        Page<BookListDto> page = bookRepository.searchPage(new Search(AUTHOR, keyword), PageRequest.ofSize(20));
        List<BookListDto> content = page.getContent();

        assertThat(content).hasSize(2);
        assertThat(content.get(0).getAuthor()).isEqualTo(keyword);
        assertThat(content.get(1).getAuthor()).isEqualTo(keyword);
    }

    @DisplayName("출판사를 타겟으로 검색어를 입력하면 출판사에 검색어가 포함된 책 목록을 반환한다.")
    @Test
    void searchPageWithPublisherKeyword() throws Exception {
        String keyword = "김영사";
        Page<BookListDto> page = bookRepository.searchPage(new Search(PUBLISHER, keyword), PageRequest.ofSize(20));
        List<BookListDto> content = page.getContent();

        assertThat(content).hasSize(2);
        assertThat(content.get(0).getPublisher()).isEqualTo(keyword);
        assertThat(content.get(1).getPublisher()).isEqualTo(keyword);
    }

    @DisplayName("제목을 내림차순으로 정렬한다.")
    @Test
    void searchPageSortByTitleDesc() throws Exception {
        Sort sort = Sort.by("title").descending();
        Page<BookListDto> page = bookRepository.searchPage(new Search(), PageRequest.of(0, 20, sort));

        List<BookListDto> contents = page.getContent();

        assertThat(contents).hasSize(4);
        assertThat(contents.get(0).getTitle()).isEqualTo("토지 2");
        assertThat(contents.get(1).getTitle()).isEqualTo("토지 1");
        assertThat(contents.get(2).getTitle()).isEqualTo("총균쇠");
        assertThat(contents.get(3).getTitle()).isEqualTo("사피엔스");
    }

    @DisplayName("저자를 내림차순, 제목을 내림차순으로 정렬한다.")
    @Test
    void searchPageSortByAuthorDescAndTitleDesc() throws Exception {
        Sort sort = Sort.by("author").descending()
                .and(Sort.by("title").descending());
        Page<BookListDto> page = bookRepository.searchPage(new Search(), PageRequest.of(0, 20, sort));

        List<BookListDto> contents = page.getContent();

        assertThat(contents).hasSize(4);
        assertThat(contents.get(0).getTitle()).isEqualTo("사피엔스");
        assertThat(contents.get(1).getTitle()).isEqualTo("총균쇠");
        assertThat(contents.get(2).getTitle()).isEqualTo("토지 2");
        assertThat(contents.get(3).getTitle()).isEqualTo("토지 1");
    }

}