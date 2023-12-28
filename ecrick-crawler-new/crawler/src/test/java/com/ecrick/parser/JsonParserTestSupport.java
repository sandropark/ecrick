package com.ecrick.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonParserTestSupport extends ParserTestSupport {
    @Autowired
    ObjectMapper objectMapper;
}
