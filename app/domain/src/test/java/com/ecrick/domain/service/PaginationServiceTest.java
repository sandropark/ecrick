package com.ecrick.domain.service;

import com.ecrick.core.dto.Pagination;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class PaginationServiceTest {

    PaginationService paginationService = new PaginationService();

    int MAX_BAR_LENGTH_OF_DESKTOP = 10;

    @DisplayName("시작 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 0", "9, 0", "10, 10", "19, 10", "20, 20", "29, 20"})
    void DesktopStartPage(int currentPage, int expected) throws Exception {
        int startPage = paginationService.getStartPage(MAX_BAR_LENGTH_OF_DESKTOP, currentPage);

        assertThat(startPage).isEqualTo(expected);
    }

    @DisplayName("끝 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 1, 0", "0, 10, 9", "0, 11, 9", "10, 11, 10", "10, 20, 19", "10, 21, 19"})
    void DesktopEndPage(int currentPage, int totalPages, int expected) throws Exception {
        int currentTotalPages = paginationService.getTotalPages(MAX_BAR_LENGTH_OF_DESKTOP, currentPage, totalPages);
        int endPage = paginationService.getEndPage(MAX_BAR_LENGTH_OF_DESKTOP, currentPage, currentTotalPages);

        assertThat(endPage).isEqualTo(expected);
    }

    @DisplayName("이전 현재 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, -1", "9, -1", "10, 9", "19, 9", "20, 19", "29, 19"})
    void preCurrentPage(int currentPage, int expected) throws Exception {
        int preCurrentPage = paginationService.getPreCurrentPage(MAX_BAR_LENGTH_OF_DESKTOP, currentPage);

        assertThat(preCurrentPage).isEqualTo(expected);
    }

    @DisplayName("다음 현재 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 1, -1", "0, 10, -1", "0, 11, 10", "10, 11, -1", "10, 21, 20"})
    void nextCurrentPage(int currentPage, int totalPages, int expected) throws Exception {
        int currentTotalPages = paginationService.getTotalPages(MAX_BAR_LENGTH_OF_DESKTOP, currentPage, totalPages);
        int nextCurrentPage = paginationService.getNextCurrentPage(MAX_BAR_LENGTH_OF_DESKTOP, currentPage, currentTotalPages);

        assertThat(nextCurrentPage).isEqualTo(expected);
    }

    @Test
    void getDesktopPagination() throws Exception {
        // When
        Pagination pagination = paginationService.getDesktopPagination(0, 11);

        // Then
        assertThat(pagination.getPageNumbers()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertThat(pagination.getPreCurrentPage()).isEqualTo(-1);
        assertThat(pagination.getNextCurrentPage()).isEqualTo(10);
    }

}