package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<BookDto> toBookDtos(Library library);
}