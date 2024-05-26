package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.KyoboXmlDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.domain.entity.Library;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.jsoup.Connection;

import java.io.StringReader;

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
            throw new RuntimeException(e);
        }
    }

    private String cleanBody(String body) {
        return BodyCleanUtils.clean(body)
                .replaceAll("&apos;", "'");
    }

}
