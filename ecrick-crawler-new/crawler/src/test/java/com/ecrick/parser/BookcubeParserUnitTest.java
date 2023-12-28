package com.ecrick.parser;

import com.ecrick.util.FileIOUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

class BookcubeParserUnitTest {

    @DisplayName("preProcess 후 Map으로 역직렬화 한다.")
    @MethodSource("source")
    @ParameterizedTest
    void preProcess(String fileName) throws Exception {
        String data = FileIOUtil.read(fileName);
        String processedData = new BookcubeParser(null).preProcess(data);
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(processedData, Map.class);
        System.out.println("map = " + map);
    }

    static Stream<Arguments> source() {
        return Arrays.stream(FileIOUtil.getFileList())
                .filter(name -> name.contains("북큐브"))
                .map(Arguments::of);
    }

}