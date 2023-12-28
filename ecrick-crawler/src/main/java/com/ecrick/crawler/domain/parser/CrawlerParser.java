package com.ecrick.crawler.domain.parser;


import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.entity.Library;

public interface CrawlerParser {
    Boolean supports(Library library);

    LibraryResponse parse(JsoupResponse response);
}
