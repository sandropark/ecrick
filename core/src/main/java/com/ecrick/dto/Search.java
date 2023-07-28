package com.ecrick.dto;

import com.ecrick.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Search {
    private SearchTarget searchTarget = SearchTarget.TOTAL;
    private String keyword;

    public Search() {}
}
