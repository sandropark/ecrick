package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.dto.SeoulLibDto;

public class SeoulLibParser extends CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isSeoulLib();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseJson(SeoulLibDto.class, bodyDto.getBody());
    }

}
