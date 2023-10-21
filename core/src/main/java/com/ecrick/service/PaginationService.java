package com.ecrick.service;

import com.ecrick.dto.Pagination;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

@Service
public class PaginationService {

    private static final int DESKTOP_MAX_BAR_LENGTH = 10;
    private static final int MOBILE_MAX_BAR_LENGTH = 5;

    public Pagination getDesktopPagination(int currentPage, int totalPages) {
        return getPagination(DESKTOP_MAX_BAR_LENGTH, currentPage, totalPages);
    }
    public Pagination getMobilePagination(int currentPage, int totalPages) {
        return getPagination(MOBILE_MAX_BAR_LENGTH, currentPage, totalPages);
    }

    private Pagination getPagination(int maxBarLength, int currentPage, int totalPages) {
        int currentTotalPages = getTotalPages(maxBarLength, currentPage, totalPages);

        int startPage = getStartPage(maxBarLength, currentPage);
        int endPage = getEndPage(maxBarLength, currentPage, currentTotalPages);
        int preCurrentPage = getPreCurrentPage(maxBarLength, currentPage);
        int nextCurrentPage = getNextCurrentPage(maxBarLength, currentPage, currentTotalPages);

        List<Integer> barNumbers = IntStream.range(startPage, endPage+1).boxed().collect(Collectors.toList());

        return new Pagination(barNumbers, preCurrentPage, nextCurrentPage);
    }

    protected int getTotalPages(int maxBarLength, int currentPage, int totalPages) {
        return currentPage >= maxBarLength ? totalPages - getStartPage(maxBarLength, currentPage) : totalPages;
    }

    protected int getStartPage(int maxBarLength, int currentPage) {
        return currentPage / maxBarLength * maxBarLength;
    }

    protected int getEndPage(int maxBarLength, int currentPage, int totalPages) {
        return min(totalPages, maxBarLength) - 1 + getStartPage(maxBarLength, currentPage);
    }

    protected int getPreCurrentPage(int maxBarLength, int currentPage) {
        return currentPage < maxBarLength ? -1 : getStartPage(maxBarLength, currentPage) - 1;
    }

    protected int getNextCurrentPage(int maxBarLength, int currentPage, int totalPages) {
        return totalPages <= maxBarLength ? -1 : getEndPage(maxBarLength, currentPage, totalPages) + 1;
    }
}
