package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.ecrick.model.KyoboJsonCrawlerModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class KyoboJsonParser extends CrawlerParser {

    public KyoboJsonParser(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean supports(Library library) {
        return library.isKyoboJson();
    }

    @Override
    public CommonModel parse(String body) {
        return parseJson(body, KyoboJsonCrawlerModel.class);
    }

    private String cleanBody(String body) {
        return body.replaceAll("&amp;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "");
    }
}
