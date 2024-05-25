package com.ecrick.domain.entity;

import lombok.Getter;

@Getter
public enum VendorName {
    KYOBO("교보"), YES24("예스24"), BOOKCUBE("북큐브"), OPMS("OPMS"), ALADIN("알라딘"), SEOUL_LIB("서울도서관"), SEOUL_EDU("서울시교육청");

    private final String value;

    VendorName(String value) {
        this.value = value;
    }

    public Boolean isKyobo() {
        return this == KYOBO;
    }

    public Boolean isYes24() {
        return this == YES24;
    }

    public Boolean isBookcube() {
        return this == BOOKCUBE;
    }

    public Boolean isOPMS() {
        return this == OPMS;
    }

    public Boolean isAladin() {
        return this == ALADIN;
    }

    public Boolean isSeoulLib() {
        return this == SEOUL_LIB;
    }

    public Boolean isSeoulEdu() {
        return this == SEOUL_EDU;
    }
}
