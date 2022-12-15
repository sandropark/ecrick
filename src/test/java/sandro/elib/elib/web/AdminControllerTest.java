package sandro.elib.elib.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sandro.elib.elib.crawler.CrawlerService;
import sandro.elib.elib.crawler.UpdateCrawlerService;
import sandro.elib.elib.dto.LibraryDto;
import sandro.elib.elib.dto.Pagination;
import sandro.elib.elib.service.LibraryService;
import sandro.elib.elib.service.PaginationService;
import sandro.elib.elib.web.dto.LibraryAddFormDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sandro.elib.elib.web.AdminController.ADMIN_LIBRARIES;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired MockMvc mvc;
    @MockBean PaginationService paginationService;
    @MockBean LibraryService libraryService;
    @MockBean CrawlerService crawlerService;
    @MockBean UpdateCrawlerService updateCrawlerService;

    @DisplayName("[GET] 관리자-도서관 목록 조회")
    @Test
    void libraries() throws Exception {
        // Given
        given(libraryService.searchLibrary(any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPagination(anyInt(), anyInt())).willReturn(new Pagination(List.of(), 0, 0));

        // When
        mvc.perform(get(ADMIN_LIBRARIES))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/libraries/index"))
                .andExpect(model().attributeExists("libraries"))
                .andExpect(model().attributeExists("pagination"));

        // Then
        then(libraryService).should().searchLibrary(any(Pageable.class));
        then(paginationService).should().getPagination(anyInt(), anyInt());
    }

    @DisplayName("[GET] 도서관 상세페이지")
    @Test
    void libraryDetail() throws Exception {
        // Given
        Long libraryId = 1L;
        given(libraryService.getLibraryDto(libraryId)).willReturn(LibraryDto.of());

        // When
        mvc.perform(get(ADMIN_LIBRARIES + "/" + libraryId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("library"))
                .andExpect(view().name("admin/libraries/detail"));

        // Then
        then(libraryService).should().getLibraryDto(libraryId);
    }

    @DisplayName("[POST] 도서관 삭제")
    @Test
    void deleteLibrary() throws Exception {
        // Given
        Long libraryId = 1L;
        willDoNothing().given(libraryService).delete(libraryId);

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/" + libraryId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ADMIN_LIBRARIES));

        // Then
        then(libraryService).should().delete(libraryId);
    }

    @DisplayName("[POST] 해당 도서관 크롤링")
    @Test
    void crawl() throws Exception {
        // Given
        willDoNothing().given(crawlerService).run();

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/1/crawl")
                        .param("keyword", "")
                        .param("page", "")
                        .param("sort", "")
                        .param("size", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ADMIN_LIBRARIES));

        // Then
        then(crawlerService).should().run();
    }

    @DisplayName("[POST] 해당 도서관 저장된 도서수 업데이트")
    @Test
    void updateSavedBooks() throws Exception {
        // Given
        Long libraryId = 1L;
        willDoNothing().given(libraryService).updateSavedBooks(libraryId);

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/" + libraryId + "/saved-update")
                        .param("keyword", "")
                        .param("page", "")
                        .param("sort", "")
                        .param("size", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ADMIN_LIBRARIES));

        // Then
        then(libraryService).should().updateSavedBooks(libraryId);
    }

    @DisplayName("[GET] 도서관 추가 페이지")
    @Test
    void addForm() throws Exception {
        // When & Then
        mvc.perform(get(ADMIN_LIBRARIES + "/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("library"))
                .andExpect(view().name("admin/libraries/add-form"));
    }

    @DisplayName("[POST] 도서관 저장")
    @Test
    void save() throws Exception {
        // Given
        willDoNothing().given(libraryService).save(any(LibraryAddFormDto.class));

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/form")
                        .param("name", "서울"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ADMIN_LIBRARIES));

        // Then
        then(libraryService).should().save(any(LibraryAddFormDto.class));
    }

    @DisplayName("[GET] 도서관 수정 페이지")
    @Test
    void editForm() throws Exception {
        // Given
        Long libraryId = 1L;
        given(libraryService.getLibraryDto(libraryId)).willReturn(LibraryDto.of());

        // When
        mvc.perform(get(ADMIN_LIBRARIES + "/" + libraryId + "/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("library"))
                .andExpect(view().name("admin/libraries/edit-form"));

        // Then
        then(libraryService).should().getLibraryDto(libraryId);
    }

    @DisplayName("[POST] 도서관 수정")
    @Test
    void update() throws Exception {
        // Given
        Long libraryId = 1L;
        willDoNothing().given(libraryService).update(any(LibraryDto.class));

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/" + libraryId + "/form"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ADMIN_LIBRARIES + "/" + libraryId));

        // Then
        then(libraryService).should().update(any(LibraryDto.class));
    }

}
