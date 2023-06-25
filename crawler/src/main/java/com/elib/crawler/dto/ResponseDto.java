package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.CoreDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<CoreDto> toCoreDtos(Library library);
}