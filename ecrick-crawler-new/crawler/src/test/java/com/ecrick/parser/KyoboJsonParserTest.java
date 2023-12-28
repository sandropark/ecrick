package com.ecrick.parser;

import com.ecrick.util.FileIOUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

class KyoboJsonParserTest extends JsonParserTestSupport {

    @MethodSource
    @ParameterizedTest
    void parse(String fileName) {
        parse(new KyoboJsonParser(objectMapper), fileName);
    }

    static Stream<Arguments> parse() {
        return Arrays.stream(FileIOUtil.getFileList())
                .filter(name -> name.contains("교보.json"))
                .map(Arguments::of);
    }

    @Disabled
    @MethodSource
    @ParameterizedTest
    void error(String fileName) throws Exception {
        parse(new KyoboJsonParser(objectMapper), fileName);
    }

    static Stream<Arguments> error() {
        return Arrays.stream(FileIOUtil.getErrorFileList())
                .filter(name -> name.contains("교보.json"))
                .map(Arguments::of);
    }

}