package com.ecrick.crawler.web.controller;

import com.ecrick.crawler.domain.service.CrawlerService;
import com.ecrick.domain.dto.LibraryDto;
import com.ecrick.domain.dto.Pagination;
import com.ecrick.domain.dto.VendorDto;
import com.ecrick.domain.entity.VendorName;
import com.ecrick.domain.service.CoreService;
import com.ecrick.domain.service.LibraryService;
import com.ecrick.domain.service.PaginationService;
import com.ecrick.domain.service.VendorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.ecrick.crawler.web.controller.LibraryController.ADMIN_LIBRARIES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LibraryController.class)
class LibraryControllerTest {

    @Autowired MockMvc mvc;
    @MockBean
    PaginationService paginationService;
    @MockBean
    LibraryService libraryService;
    @MockBean CrawlerService crawlerService;
    @MockBean
    VendorService vendorService;
    @MockBean
    CoreService coreService;

    @DisplayName("[GET] 관리자-도서관 목록 조회")
    @Test
    void libraries() throws Exception {
        // Given
        given(libraryService.searchLibrary(any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getDesktopPagination(anyInt(), anyInt())).willReturn(new Pagination(List.of(), 0, 0));

        // When
        mvc.perform(get(ADMIN_LIBRARIES))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("libraries/list"))
                .andExpect(model().attributeExists("libraries"))
                .andExpect(model().attributeExists("pagination"));

        // Then
        then(libraryService).should().searchLibrary(any(Pageable.class));
        then(paginationService).should().getDesktopPagination(anyInt(), anyInt());
    }

    @DisplayName("[GET] 도서관 추가 페이지")
    @Test
    void addForm() throws Exception {
        // When & Then
        mvc.perform(get(ADMIN_LIBRARIES + "/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("contentTypes"))
                .andExpect(model().attributeExists("vendorList"))
                .andExpect(view().name("libraries/addForm"));
    }

    @DisplayName("[POST] 도서관 저장")
    @Test
    void saveLibrary() throws Exception {
        // Given
        willDoNothing().given(libraryService).saveLibrary(any(LibraryDto.class), anyLong());

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/form")
                        .param("vendorId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + ADMIN_LIBRARIES));

        // Then
        then(libraryService).should().saveLibrary(any(LibraryDto.class), anyLong());
    }

    @DisplayName("[GET] 도서관 상세페이지")
    @Test
    void libraryDetail() throws Exception {
        // Given
        Long libraryId = 1L;
        LibraryDto dto = LibraryDto.builder()
                .vendor(VendorDto.builder().name(VendorName.KYOBO).build())
                .build();
        given(libraryService.getLibrary(libraryId)).willReturn(dto);

        // When
        mvc.perform(get(ADMIN_LIBRARIES + "/" + libraryId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("library"))
                .andExpect(view().name("libraries/detail"));

        // Then
        then(libraryService).should().getLibrary(libraryId);
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

    @DisplayName("[GET] 도서관 수정 페이지")
    @Test
    void editForm() throws Exception {
        // Given
        Long libraryId = 1L;
        LibraryDto dto = LibraryDto.builder()
                .vendor(VendorDto.builder().name(VendorName.KYOBO).build())
                .build();
        given(libraryService.getLibrary(libraryId)).willReturn(dto);

        // When
        mvc.perform(get(ADMIN_LIBRARIES + "/" + libraryId + "/form"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("form"))
                .andExpect(model().attributeExists("contentTypes"))
                .andExpect(model().attributeExists("vendorList"))
                .andExpect(view().name("libraries/editForm"));

        // Then
        then(libraryService).should().getLibrary(libraryId);
    }

    @DisplayName("[POST] 도서관 수정")
    @Test
    void update() throws Exception {
        // Given
        Long libraryId = 1L;
        willDoNothing().given(libraryService).libraryUpdate(any(LibraryDto.class), anyLong());

        // When
        mvc.perform(post(ADMIN_LIBRARIES + "/" + libraryId + "/form")
                        .param("vendorId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ADMIN_LIBRARIES + "/" + libraryId));

        // Then
        then(libraryService).should().libraryUpdate(any(LibraryDto.class), anyLong());
    }

}

