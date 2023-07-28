package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.dto.Yes24JsonDto;

public class Yes24JsonParser extends CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isYes24Json();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseJson(Yes24JsonDto.class, bodyDto.getBody());
    }

}
