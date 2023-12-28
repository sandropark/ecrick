package com.ecrick.parser;

import com.ecrick.model.CommonModel;
import com.ecrick.model.SeoulLibCrawlerModel;
import com.ecrick.entity.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SeoulLibParser extends CrawlerParser {

    public SeoulLibParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isSeoulLib();
    }

    @Override
    public CommonModel parse(String body) {
        return parseJson(body, SeoulLibCrawlerModel.class);
    }
}
