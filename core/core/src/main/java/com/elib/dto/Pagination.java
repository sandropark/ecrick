package com.elib.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Pagination {

    private List<Integer> pageNumbers;
    private int preCurrentPage;
    private int nextCurrentPage;

    public boolean hasPrePages() {
        return preCurrentPage > -1;
    }

    public boolean hasNextPages() {
        return nextCurrentPage > -1;
    }

}
