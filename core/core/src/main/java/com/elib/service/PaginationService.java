package com.elib.service;

import org.springframework.stereotype.Service;
import com.elib.dto.Pagination;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.min;

@Service
public class PaginationService {

    public Pagination getDesktopPagination(int currentPage, int totalPages) {
        return getPagination(10, currentPage, totalPages);
    }
    public Pagination getMobilePagination(int currentPage, int totalPages) {
        return getPagination(5, currentPage, totalPages);
    }

    private Pagination getPagination(int maxBarLength, int currentPage, int totalPages) {
        int startPage = getStartPage(maxBarLength, currentPage);
        int endPage = getEndPage(maxBarLength, currentPage, totalPages);
        int preCurrentPage = getPreCurrentPage(maxBarLength, currentPage);
        int nextCurrentPage = getNextCurrentPage(maxBarLength, currentPage, totalPages);
        List<Integer> barNumbers = IntStream.range(startPage, endPage+1).boxed().collect(Collectors.toList());

        return new Pagination(barNumbers, preCurrentPage, nextCurrentPage);
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
