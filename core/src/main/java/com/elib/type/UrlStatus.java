package com.elib.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UrlStatus {
    OK("정상"),
    ER("오류");

    private final String description;
}
