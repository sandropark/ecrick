package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.KyoboJsonDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.crawler.infra.utils.ObjectMapperUtils;
import com.ecrick.domain.entity.Library;
import org.springframework.stereotype.Component;

import static org.jsoup.Connection.Response;

@Component
public class KyoboJsonParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isKyoboJson();
    }

    @Override
    public ResponseDto parse(Response response) {
        return ObjectMapperUtils
                .readValue(cleanBody(response.body()), KyoboJsonDto.class);
    }

    private String cleanBody(String body) {
        return BodyCleanUtils.clean(body)
                .replaceAll("&amp;|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&AElig;", "");
    }
}
