package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.ecrick.model.Yes24XmlCrawlerModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Yes24XmlParser extends CrawlerParser {

    public Yes24XmlParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isYes24Xml();
    }

    @Override
    public CommonModel parse(String body) {
        return parseXml(body, Yes24XmlCrawlerModel.class);
    }

    private String cleanBody(String body) {
        return body.replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");
    }
}
