package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.crawler.domain.exception.ExceptionCode;
import com.ecrick.crawler.infra.dto.Yes24XmlDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.domain.entity.Library;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class Yes24XmlParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isYes24Xml();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        try {
            return (ResponseDto) JAXBContext
                    .newInstance(Yes24XmlDto.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(cleanBody(response.body()))); // xml
        } catch (JAXBException e) {
            throw new CrawlerException(ExceptionCode.PARSING_FAILED, e);
        }
    }

    private String cleanBody(String body) {
        return BodyCleanUtils.clean(body)
                .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");

    }
}
