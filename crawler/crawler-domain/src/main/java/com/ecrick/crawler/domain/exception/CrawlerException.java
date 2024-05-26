package com.ecrick.crawler.domain.exception;

public class CrawlerException extends RuntimeException {
    private ExceptionCode exceptionCode;

    public CrawlerException(String message) {
        super(message);
    }

    public CrawlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
