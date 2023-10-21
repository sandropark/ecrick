package com.ecrick.crawler.dto;

import com.ecrick.domain.Library;
import com.ecrick.dto.CoreDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<CoreDto> toCoreDtos(Library library);
}