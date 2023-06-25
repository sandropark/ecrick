package com.elib.dto;

import com.elib.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.elib.service.SearchTarget.*;

@AllArgsConstructor
@Getter @Setter
public class Search {
    private SearchTarget searchTarget = TOTAL;
    private String keyword;

    public Search() {}
}
