package com.ecrick.service;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;

import java.util.List;

public interface Crawler {
    CrawlerModel healthCheck(Library library);

    List<RowBook> crawl(Library library);
}
