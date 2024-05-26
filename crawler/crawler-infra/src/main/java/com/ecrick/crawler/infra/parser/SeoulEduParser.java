package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.SeoulEduDto;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;

import java.io.IOException;

public class SeoulEduParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isSeoulEdu();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        try {
            return new SeoulEduDto().init(response.parse());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
