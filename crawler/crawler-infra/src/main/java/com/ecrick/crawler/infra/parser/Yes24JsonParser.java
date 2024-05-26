package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.Yes24JsonDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.crawler.infra.utils.ObjectMapperUtils;
import com.ecrick.domain.entity.Library;
import org.springframework.stereotype.Component;

import static org.jsoup.Connection.Response;

@Component
public class Yes24JsonParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isYes24Json();
    }

    @Override
    public ResponseDto parse(Response response) {
        return ObjectMapperUtils.readValue(BodyCleanUtils.clean(response.body()), Yes24JsonDto.class);
    }
}
