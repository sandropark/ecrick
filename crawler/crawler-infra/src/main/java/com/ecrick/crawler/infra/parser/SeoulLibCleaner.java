package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.SeoulLibDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.crawler.infra.utils.ObjectMapperUtils;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;

public class SeoulLibCleaner implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isSeoulLib();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        return ObjectMapperUtils.readValue(BodyCleanUtils.clean(response.body()), SeoulLibDto.class);
    }

}
