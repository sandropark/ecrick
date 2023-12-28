package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.AladinCrawlerModel;
import com.ecrick.model.CommonModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class AladinParser extends CrawlerParser {
    public AladinParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isAladin();
    }

    @Override
    public CommonModel parse(String body) {
        return parseXml(body, AladinCrawlerModel.class);
    }

}
