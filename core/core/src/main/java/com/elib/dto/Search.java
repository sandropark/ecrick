package com.elib.dto;

import com.elib.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Search {
    private SearchTarget searchTarget;
    private String keyword;

    public Search() {}
}
