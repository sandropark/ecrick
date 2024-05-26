package com.ecrick.crawler.domain;

import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();

    List<CoreDto> toCoreDtos(Library library);
}