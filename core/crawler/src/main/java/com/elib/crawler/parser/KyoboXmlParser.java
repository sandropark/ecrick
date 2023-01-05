package com.elib.crawler.parser;

import com.elib.crawler.CrawlerParser;
import com.elib.crawler.dto.KyoboXmlDto;
import com.elib.crawler.dto.ResponseDto;
import com.elib.domain.Library;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

import static org.jsoup.Connection.Response;

public class KyoboXmlParser implements CrawlerParser {

    @Override
    public Boolean supports(Library library) {
        return library.isKyoboXml();
    }

    @Override
    public ResponseDto parse(Response response) throws JAXBException {
        return (ResponseDto) JAXBContext
                .newInstance(KyoboXmlDto.class)
                .createUnmarshaller()
                .unmarshal(new StringReader(getBody(response)));
    }

    private String getBody(Response response) {
        return response.body()
                .replaceAll("&apos;", "'");
    }

}
