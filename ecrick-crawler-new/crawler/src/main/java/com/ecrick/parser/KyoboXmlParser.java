package com.ecrick.parser;

import com.ecrick.model.CommonModel;
import com.ecrick.model.KyoboXmlCrawlerModel;
import com.ecrick.entity.Library;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class KyoboXmlParser extends CrawlerParser {

    public KyoboXmlParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isKyoboXml();
    }

    @Override
    public CommonModel parse(String body) {
        return parseXml(body, KyoboXmlCrawlerModel.class);
    }

    private String cleanBody(String body) {
        return body.replaceAll("&apos;", "'");
    }
}
