package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.domain.entity.Library;

import static org.jsoup.Connection.Response;

public interface CrawlerParser {
    Boolean supports(Library library);

    ResponseDto parse(Response response);
}
