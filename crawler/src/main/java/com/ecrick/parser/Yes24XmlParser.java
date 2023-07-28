package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.dto.Yes24XmlDto;

public class Yes24XmlParser extends CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isYes24Xml();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return super.parseXml(Yes24XmlDto.class, bodyDto.getBody());
    }

}
