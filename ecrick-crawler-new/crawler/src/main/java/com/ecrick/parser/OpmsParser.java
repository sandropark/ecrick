package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.ecrick.model.OPMSCrawlerModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OpmsParser extends CrawlerParser {

    public OpmsParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isOPMS();
    }

    @Override
    public CommonModel parse(String body) {
        return parseJson(body, OPMSCrawlerModel.class);
    }

}
