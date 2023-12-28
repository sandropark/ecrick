package com.ecrick.crawler.service.port;

public interface BookRepository {
    void insertBookFromCore();

    Long count();
}
