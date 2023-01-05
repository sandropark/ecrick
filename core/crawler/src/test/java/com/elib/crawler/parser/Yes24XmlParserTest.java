package com.elib.crawler.parser;

import com.elib.crawler.dto.Yes24XmlDto;
import com.elib.dto.BookDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.elib.crawler.CrawlerUtil.requestUrl;

class Yes24XmlParserTest {

    @Test
    void parse() throws Exception {
        // Given
        String url = "http://ebook.gunpolib.go.kr:8082/xml.asp?viewResultCount=50";
        Yes24XmlParser parser = new Yes24XmlParser();

        // When
        Yes24XmlDto responseDto = (Yes24XmlDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + responseDto.getTotalBooks());

        List<BookDto> bookDtos = responseDto.toBookDtos(null);
        bookDtos.forEach(System.out::println);
    }

    @Test
    void parse2() throws Exception {
        // Given
        String url = "http://elib.gbgs.go.kr/yes24/xml.asp?currentPage=11&viewResultCount=500";
        Yes24XmlParser parser = new Yes24XmlParser();

        // When
        Yes24XmlDto responseDto = (Yes24XmlDto) parser.parse(requestUrl(url));

        // Then
        System.out.println("totalBooks = " + responseDto.getTotalBooks());

        List<BookDto> bookDtos = responseDto.toBookDtos(null);
        bookDtos.forEach(System.out::println);
    }

}