package com.ecrick.web.controller;

import com.ecrick.core.dto.BookDetailDto;
import com.ecrick.core.dto.BookListDto;
import com.ecrick.core.dto.Pagination;
import com.ecrick.core.service.BookService;
import com.ecrick.core.service.PaginationService;
import com.ecrick.core.service.SearchTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired MockMvc mvc;
    @MockBean BookService bookService;
    @MockBean PaginationService paginationService;

    @BeforeEach
    void setUp() {
        Page<BookListDto> page = Page.empty();
        Pagination pagination = new Pagination(List.of(1), -1, -1);

        given(bookService.searchPage(any(), any())).willReturn(page);
        given(paginationService.getDesktopPagination(page.getNumber(), page.getTotalPages())).willReturn(pagination);
    }

    @DisplayName("[GET][/books] 검색 옵션의 기본값은 통합검색이다.")
    @Test
    void bookListSearchDefaultSelected() throws Exception {
        MvcResult result = mvc.perform(get("/books")
                        .header(HttpHeaders.USER_AGENT, ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString();
        assertThat(html).contains("value=\"TOTAL\" selected=\"selected\"");
    }

    @DisplayName("[GET][/books] 검색하는 경우")
    @Test
    void bookListSearch() throws Exception {
        String searchTarget = SearchTarget.TITLE.name();
        String keyword = "토지";

        MvcResult result = mvc.perform(get("/books")
                        .header(HttpHeaders.USER_AGENT, "")
                        .param("searchTarget", searchTarget)
                        .param("keyword", keyword)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString();
        assertThat(html).contains(String.format("value=\"%s\" selected=\"selected\"", searchTarget));
        assertThat(html).contains(String.format("value=\"%s\"", keyword));
    }

    @DisplayName("[GET][/books/{bookId}] 책 상세보기")
    @Test
    void bookDetail() throws Exception {
        Long bookId = 1L;
        given(bookService.getBookDetail(bookId)).willReturn(BookDetailDto.empty());

        MvcResult result = mvc.perform(get("/books/" + bookId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String html = result.getResponse().getContentAsString();
        assertThat(html).contains("value=\"TOTAL\" selected=\"selected\"");
    }

}