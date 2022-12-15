package sandro.elib.elib.crawler.dto;

import sandro.elib.elib.dto.BookDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<BookDto> toBookDto();
    List<String> getDetailUrl(String apiUrl);
}