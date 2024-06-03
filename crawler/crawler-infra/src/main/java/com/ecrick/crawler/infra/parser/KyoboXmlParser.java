package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.domain.exception.CrawlerException;
import com.ecrick.crawler.domain.exception.ExceptionCode;
import com.ecrick.crawler.infra.dto.KyoboXmlDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Component
public class KyoboXmlParser implements CrawlerParser {

    @Override
    public Boolean supports(Library library) {
        return library.isKyoboXml();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        try {
            return (ResponseDto) JAXBContext
                    .newInstance(KyoboXmlDto.class)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(cleanBody(response.body())));
        } catch (JAXBException e) {
            throw new CrawlerException(ExceptionCode.PARSING_FAILED, e);
        }
    }

    private String cleanBody(String body) {
        return BodyCleanUtils.clean(body)
                .replaceAll("&apos;", "'");
    }

}
