package com.ecrick.dto;

import com.ecrick.service.SearchTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.ecrick.service.SearchTarget.*;

@AllArgsConstructor
@Getter @Setter
public class Search {
    private SearchTarget searchTarget = TOTAL;
    private String keyword;

    public Search() {}
}
