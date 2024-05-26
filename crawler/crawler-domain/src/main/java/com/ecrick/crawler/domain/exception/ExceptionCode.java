package com.ecrick.crawler.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    REQUEST_FAILED("요청 실패"),
    PARSING_FAILED("파싱 실패");

    private final String message;
}
