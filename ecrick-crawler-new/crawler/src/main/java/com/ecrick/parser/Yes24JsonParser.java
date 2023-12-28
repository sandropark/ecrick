package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.ecrick.model.Yes24JsonCrawlerModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Yes24JsonParser extends CrawlerParser {

    public Yes24JsonParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isYes24Json();
    }

    @Override
    public CommonModel parse(String body) {
        return parseJson(body, Yes24JsonCrawlerModel.class);
    }
}
