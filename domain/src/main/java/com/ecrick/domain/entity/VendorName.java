package com.ecrick.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VendorName {
    KYOBO("교보"),
    YES24("예스24"),
    BOOKCUBE("북큐브"),
    OPMS("OPMS"),
    ALADIN("알라딘"),
    SEOUL_LIB("서울도서관"),
    SEOUL_EDU("서울시교육청");

    private final String value;

    public boolean isKyobo() {
        return this == KYOBO;
    }

    public boolean isYes24() {
        return this == YES24;
    }

    public boolean isBookcube() {
        return this == BOOKCUBE;
    }

    public boolean isOPMS() {
        return this == OPMS;
    }

    public boolean isAladin() {
        return this == ALADIN;
    }

    public boolean isSeoulLib() {
        return this == SEOUL_LIB;
    }

    public boolean isSeoulEdu() {
        return this == SEOUL_EDU;
    }
}
