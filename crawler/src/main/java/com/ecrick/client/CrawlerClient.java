package com.ecrick.client;

import com.ecrick.dto.LibraryCrawlerDto;
import com.ecrick.dto.ResponseDto;
import com.ecrick.parser.*;
import com.ecrick.domain.Library;
import com.ecrick.dto.CoreDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CrawlerClient {
    public abstract Boolean exchange(Library library);

    protected String makeUrl(Library library) {
        String[] params = library.getParam().split("&");
        String tempUrl = library.getUrl() + params[0] + "2";
        if (params.length > 1)
            return tempUrl + "&" + params[1] + "1";
        return tempUrl;
    }

    protected void log(String url, ResponseDto responseDto, CoreDto coreDto) {
        Library library = coreDto.getLibrary();
        log.info("{}, url={}, vendor={}, contentType={}, totalBooks={}, title={}, author={}, publisher={}",
                library.getName(), url, library.getVendor().getName(), library.getContentType(),
                responseDto.getTotalBooks(), coreDto.getTitle(), coreDto.getAuthor(), coreDto.getPublisher());
    }

    protected CrawlerParser findParser(LibraryCrawlerDto library) {
        if (library.isAladin()) return new AladinParser();
        if (library.isBookcube()) return new BookcubeParser();
        if (library.isKyoboJson()) return new KyoboJsonParser();
        if (library.isKyoboXml()) return new KyoboXmlParser();
        if (library.isOPMS()) return new OpmsParser();
        if (library.isSeoulEdu()) return new SeoulEduParser();
        if (library.isSeoulLib()) return new SeoulLibParser();
        if (library.isYes24Json()) return new Yes24JsonParser();
        if (library.isYes24Xml()) return new Yes24XmlParser();

        throw new IllegalArgumentException();
    }

}