package com.ecrick.crawler.domain.exception;

import lombok.Getter;

@Getter
public class CrawlerException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public CrawlerException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public CrawlerException(ExceptionCode exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public CrawlerException(ExceptionCode exceptionCode, Throwable cause) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }
}
