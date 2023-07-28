package com.ecrick.parser;

import com.ecrick.dto.KyoboXmlDto;
import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;

public class KyoboXmlParser extends CrawlerParser {

    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isKyoboXml();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseXml(KyoboXmlDto.class, bodyDto.getBody());
    }

}
