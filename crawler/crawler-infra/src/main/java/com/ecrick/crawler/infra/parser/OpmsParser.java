package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.OPMSDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.crawler.infra.utils.ObjectMapperUtils;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

@Component
public class OpmsParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isOPMS();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        return ObjectMapperUtils.readValue(BodyCleanUtils.clean(response.body()), OPMSDto.class);
    }
}
