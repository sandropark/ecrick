package com.ecrick.crawler.domain.parser;

import com.ecrick.crawler.domain.webclient.JsoupResponse;
import com.ecrick.crawler.domain.webclient.response.LibraryResponse;
import com.ecrick.crawler.domain.webclient.response.SeoulEduResponse;
import com.ecrick.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class SeoulEduParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isSeoulEdu();
    }

    @Override
    public LibraryResponse parse(JsoupResponse response) {
        return new SeoulEduResponse(response.document());
    }
}
