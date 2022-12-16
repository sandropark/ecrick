package com.elib.service;

import org.springframework.stereotype.Service;
import com.elib.dto.Pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 10;

    public Pagination getPagination(int currentPage, int totalPages) {
        if (totalPages == 0) {
            return new Pagination(List.of(0), 0, 0);
        }
        int startPage = getStartPage(currentPage);
        int endPage = getEndPage(currentPage, totalPages);
        List<Integer> barNumbers = IntStream.range(startPage, endPage).boxed().collect(Collectors.toList());
        int preStartPage = getPreStartPage(currentPage);
        int nextStartPage = getNextStartPage(currentPage, totalPages);
        return new Pagination(barNumbers, preStartPage, nextStartPage);
    }

    private static int getStartPage(int currentPage) {
        return currentPage / BAR_LENGTH * BAR_LENGTH;
    }

    private static int getEndPage(int currentPage, int totalPages) {
        return min(totalPages, currentPage / BAR_LENGTH * BAR_LENGTH + BAR_LENGTH);
    }

    private static int getPreStartPage(int currentPage) {
        return max(currentPage / BAR_LENGTH * BAR_LENGTH - BAR_LENGTH, 0);
    }

    private static int getNextStartPage(int currentPage, int totalPages) {
        return min(totalPages-1, currentPage / BAR_LENGTH * BAR_LENGTH + 10);
    }

}
