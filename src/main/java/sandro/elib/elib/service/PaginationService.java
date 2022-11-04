package sandro.elib.elib.service;

import org.springframework.stereotype.Service;
import sandro.elib.elib.dto.Pagination;

import static java.lang.Math.*;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 10;

    public Pagination getPagination(int currentPage, int totalPages) {
        Pagination pagination = new Pagination();
        pagination.setStartPage(getStartPage(currentPage));
        pagination.setEndPage(getEndPage(currentPage, totalPages));
        pagination.setPreStartPage(getPreStartPage(currentPage));
        pagination.setNextStartPage(getNextStartPage(currentPage, totalPages));
        return pagination;
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
