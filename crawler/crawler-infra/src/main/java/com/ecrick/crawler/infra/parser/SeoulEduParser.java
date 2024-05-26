package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.crawler.domain.exception.ExceptionCode;
import com.ecrick.crawler.infra.dto.SeoulEduDto;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
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
            throw new CrawlerException(ExceptionCode.PARSING_FAILED, e);
        }
    }
}
