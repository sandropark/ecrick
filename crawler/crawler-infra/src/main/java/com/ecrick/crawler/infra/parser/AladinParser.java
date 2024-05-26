package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.crawler.domain.exception.ExceptionCode;
import com.ecrick.crawler.infra.dto.AladinDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.domain.entity.Library;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class AladinParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isAladin();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        try {
            return (ResponseDto) JAXBContext.newInstance(AladinDto.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(BodyCleanUtils.clean(response.body())));
        } catch (JAXBException e) {
            throw new CrawlerException(ExceptionCode.PARSING_FAILED, e);
        }
    }
}
