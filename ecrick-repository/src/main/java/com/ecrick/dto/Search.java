package com.ecrick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ecrick.dto.SearchTarget.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Search {
    private SearchTarget searchTarget = TOTAL;
    private String keyword;
}
