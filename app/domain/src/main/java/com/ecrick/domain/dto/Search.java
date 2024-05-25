package com.ecrick.domain.dto;

import com.ecrick.domain.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.ecrick.domain.service.SearchTarget.TOTAL;

@AllArgsConstructor
@Getter
@Setter
public class Search {
    private SearchTarget searchTarget = TOTAL;
    private String keyword;

    public Search() {
    }
}
