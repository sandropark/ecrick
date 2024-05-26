package com.ecrick.crawler.domain;

import com.ecrick.domain.entity.Library;

public interface CrawlerClient {
    ResponseDto get(Library library);

    void updateTotalBooks(Library library);
}
