package com.ecrick.crawler.dto;

import com.ecrick.core.domain.Library;
import com.ecrick.core.dto.CoreDto;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();

    List<CoreDto> toCoreDtos(Library library);
}