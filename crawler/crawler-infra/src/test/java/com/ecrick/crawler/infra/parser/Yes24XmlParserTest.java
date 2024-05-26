package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.dto.Yes24XmlDto;
import com.ecrick.domain.dto.CoreDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.ecrick.crawler.domain.worker.CrawlerUtil.requestUrl;

class Yes24XmlParserTest {

    @Disabled
    @Test
    void parse() throws Exception {
        // Given
        String url = "http://ebook.gunpolib.go.kr:8082/xml.asp?viewResultCount=50";
        Yes24XmlParser parser = new Yes24XmlParser();

        // When
        Yes24XmlDto responseDto = (Yes24XmlDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + responseDto.getTotalBooks());

        List<CoreDto> coreDtos = responseDto.toCoreDtos(null);
        coreDtos.forEach(System.out::println);
    }

    @Disabled
    @Test
    void parse2() throws Exception {
        // Given
        String url = "http://elib.gbgs.go.kr/yes24/xml.asp?currentPage=11&viewResultCount=500";
        Yes24XmlParser parser = new Yes24XmlParser();

        // When
        Yes24XmlDto responseDto = (Yes24XmlDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + responseDto.getTotalBooks());

        List<CoreDto> coreDtos = responseDto.toCoreDtos(null);
        coreDtos.forEach(System.out::println);
    }

}