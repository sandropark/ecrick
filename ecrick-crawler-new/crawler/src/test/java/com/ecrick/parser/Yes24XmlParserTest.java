package com.ecrick.parser;

import com.ecrick.util.FileIOUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

class Yes24XmlParserTest extends ParserTestSupport {

    @MethodSource
    @ParameterizedTest
    void parse(String fileName) {
        parse(new Yes24XmlParser(null), fileName);
    }

    static Stream<Arguments> parse() {
        return Arrays.stream(FileIOUtil.getFileList())
                .filter(name -> name.contains("예스24.xml"))
                .map(Arguments::of);
    }

}
















