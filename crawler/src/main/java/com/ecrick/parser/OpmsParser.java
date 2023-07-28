package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.OPMSDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;

public class OpmsParser extends CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isOPMS();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseJson(OPMSDto.class, bodyDto.getBody());
    }

}
