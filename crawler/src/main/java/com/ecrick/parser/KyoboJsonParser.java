package com.ecrick.parser;

import com.ecrick.dto.KyoboJsonDto;
import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;

public class KyoboJsonParser extends CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isKyoboJson();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseJson(KyoboJsonDto.class, bodyDto.getBody());
    }

}
