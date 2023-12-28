package com.ecrick.parser;

import com.ecrick.util.FileIOUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

class BookcubeParserTest extends JsonParserTestSupport {

    @MethodSource("source")
    @ParameterizedTest
    void parse(String fileName) throws Exception {
        parse(new BookcubeParser(objectMapper), fileName);
    }

    static Stream<Arguments> source() {
        return Arrays.stream(FileIOUtil.getFileList())
                .filter(name -> name.contains("북큐브"))
                .map(Arguments::of);
    }

}