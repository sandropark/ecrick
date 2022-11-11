package sandro.elib.elib.crwaler.dto;

import sandro.elib.elib.dto.BookDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<BookDto> toBookDto();
}