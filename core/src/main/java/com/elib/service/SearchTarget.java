package com.elib.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SearchTarget {
    TOTAL("통합검색"), TITLE("제목"), AUTHOR("저자"), PUBLISHER("출판사");

    private final String displayName;
}
