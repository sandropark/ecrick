package com.elib.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class PaginationServiceTest {

    PaginationService paginationService = new PaginationService();

    int maxBarLengthOfDesktop = 10;

    @DisplayName("시작 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 0", "9, 0", "10, 10", "19, 10", "20, 20", "29, 20"})
    void DesktopStartPage(int currentPage, int expected) throws Exception {
        int startPage = paginationService.getStartPage(maxBarLengthOfDesktop, currentPage);

        assertThat(startPage).isEqualTo(expected);
    }

    @DisplayName("끝 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 1, 0", "0, 10, 9", "0, 11, 9", "10, 1, 10", "10, 10, 19", "10, 11, 19"})
    void DesktopEndPage(int currentPage, int totalPages, int expected) throws Exception {
        int endPage = paginationService.getEndPage(maxBarLengthOfDesktop, currentPage, totalPages);

        assertThat(endPage).isEqualTo(expected);
    }

    @DisplayName("이전 현재 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, -1", "9, -1", "10, 9", "19, 9", "20, 19", "29, 19"})
    void preCurrentPage(int currentPage, int expected) throws Exception {
        int preCurrentPage = paginationService.getPreCurrentPage(maxBarLengthOfDesktop, currentPage);

        assertThat(preCurrentPage).isEqualTo(expected);
    }

    @DisplayName("다음 현재 페이지 - 데스크탑")
    @ParameterizedTest
    @CsvSource({"0, 1, -1", "0, 10, -1", "0, 11, 10", "10, 1, -1", "10, 11, 20"})
    void nextCurrentPage(int currentPage, int totalPages, int expected) throws Exception {
        int nextCurrentPage = paginationService.getNextCurrentPage(maxBarLengthOfDesktop, currentPage, totalPages);

        assertThat(nextCurrentPage).isEqualTo(expected);
    }

}