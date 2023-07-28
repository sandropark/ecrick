package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

public abstract class CrawlerParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public abstract Boolean supports(LibraryCrawlerDto libraryDto);

    public abstract ResponseDto parse(ResponseBodyDto bodyDto);

    protected ResponseDto parseJson(Class<? extends ResponseDto> clazz, String body) {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResponseDto parseXml(Class<? extends ResponseDto> clazz, String body) {
        try {
            return (ResponseDto) JAXBContext.newInstance(clazz)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(body));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
