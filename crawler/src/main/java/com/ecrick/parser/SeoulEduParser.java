package com.ecrick.parser;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseBodyDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.dto.SeoulEduDto;

public class SeoulEduParser extends CrawlerParser {
    @Override
    public Boolean supports(LibraryCrawlerDto libraryDto) {
        return libraryDto.isSeoulEdu();
    }

    @Override
    public ResponseDto parse(ResponseBodyDto bodyDto) {
        return new SeoulEduDto().init(bodyDto.getHtml());
    }

}
