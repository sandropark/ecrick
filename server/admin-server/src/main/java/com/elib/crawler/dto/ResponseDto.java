package com.elib.crawler.dto;

import com.elib.dto.BookDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<BookDto> toBookDto();
    List<String> getDetailUrl(String apiUrl);
}