package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SeoulEduParser extends CrawlerParser {

    public SeoulEduParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isSeoulEdu();
    }

    @Override
    public CommonModel parse(String body) {
        return parseSeoulEdu(body);
    }
}
