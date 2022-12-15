package sandro.elib.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sandro.elib.dto.Pagination;
import sandro.elib.service.PaginationService;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class PaginationServiceTest {

    PaginationService paginationService = new PaginationService();

    @DisplayName("컨텐츠가 없는 경우 (현재 페이지 0, 총 페이지 0)")
    @Test
    void noResult() throws Exception {
        Pagination pagination = paginationService.getPagination(0, 0);
        assertThat(pagination).isEqualTo(new Pagination(List.of(0), 0 ,0));
    }

    @DisplayName("현재 페이지가 9이하, 총 페이지가 10 이상")
    @MethodSource
    @ParameterizedTest
    void test(
            int currentPage,
            int totalPages,
            Pagination expected
    ) throws Exception {
        Pagination actual = paginationService.getPagination(currentPage, totalPages);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> test() {
        return Stream.of(
                Arguments.of(0, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(1, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(2, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(3, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(4, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(5, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(8, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9)),
                Arguments.of(9, 10, new Pagination(List.of(0,1,2,3,4,5,6,7,8,9), 0, 9))
        );
    }

    @DisplayName("현재 페이지가 9이하, 총 페이지가 9 이하")
    @MethodSource
    @ParameterizedTest
    void test2(
            int currentPage,
            int totalPages,
            Pagination expected
    ) throws Exception {
        Pagination actual = paginationService.getPagination(currentPage, totalPages);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> test2() {
        return Stream.of(
                Arguments.of(0, 1, new Pagination(List.of(0), 0, 0)),
                Arguments.of(1, 2, new Pagination(List.of(0, 1), 0, 1)),
                Arguments.of(2, 3, new Pagination(List.of(0, 1,2), 0, 2)),
                Arguments.of(3, 4, new Pagination(List.of(0, 1,2,3), 0, 3)),
                Arguments.of(4, 5, new Pagination(List.of(0, 1,2,3,4), 0, 4)),
                Arguments.of(5, 6, new Pagination(List.of(0, 1,2,3,4,5), 0, 5)),
                Arguments.of(8, 7, new Pagination(List.of(0, 1,2,3,4,5,6), 0, 6)),
                Arguments.of(9, 8, new Pagination(List.of(0, 1,2,3,4,5,6,7), 0, 7)),
                Arguments.of(9, 9, new Pagination(List.of(0, 1,2,3,4,5,6,7,8), 0, 8))
        );
    }

}