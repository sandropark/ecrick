package com.ecrick.parser;


import com.ecrick.entity.Library;
import com.ecrick.model.CommonModel;
import com.ecrick.model.SeoulEduCrawlerModel;
import com.ecrick.util.FileIOUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.StringReader;

@Slf4j
@RequiredArgsConstructor
public abstract class CrawlerParser {
    private final ObjectMapper objectMapper;
    @Setter
    private Library library;

    public abstract boolean supports(Library library);

    public abstract CommonModel parse(String body);

    public CommonModel parseJson(String body, Class<? extends CommonModel> clazz) {
        try {
            return objectMapper.readValue(
//                    preProcessBody(body),
                    body,
                    clazz).setLibrary(library);
        } catch (JsonProcessingException e) {
            FileIOUtil.writeError(library.getFileName(), body);
            throw new RuntimeException(e);
        }
    }

    public CommonModel parseXml(String body, Class<? extends CommonModel> clazz) {
        try {
            return ((CommonModel) JAXBContext
                    .newInstance(clazz)
                    .createUnmarshaller()
                    .unmarshal(new StringReader(preProcessBody(body)))).setLibrary(library);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public CommonModel parseSeoulEdu(String body) {
        return new SeoulEduCrawlerModel(body).setLibrary(library);
    }

    private String preProcessBody(String body) {
        return body
                .replaceAll("&[a-zA-Z]{1,5};", "");
    }
}
