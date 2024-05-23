package com.ecrick.core.dto;

import com.ecrick.core.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.ecrick.core.service.SearchTarget.TOTAL;

@AllArgsConstructor
@Getter
@Setter
public class Search {
    private SearchTarget searchTarget = TOTAL;
    private String keyword;

    public Search() {
    }
}
