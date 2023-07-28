package com.ecrick.parser;

import com.ecrick.dto.AladinDto;
import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;

public class AladinParser extends CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isAladin();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseXml(AladinDto.class, bodyDto.getBody());
    }

}
