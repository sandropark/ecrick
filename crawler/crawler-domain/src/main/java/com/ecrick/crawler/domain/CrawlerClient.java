package com.ecrick.crawler.domain;

import com.ecrick.domain.entity.Library;

import java.util.Optional;

public interface CrawlerClient {
    Optional<ResponseDto> get(Library library);

    void updateTotalBooks(Library library);

    ResponseDto request(Library library);
}
