package com.ecrick.dto;

import com.ecrick.domain.Library;

import java.util.List;

public interface ResponseDto {
    Integer getTotalBooks();
    List<CoreDto> toCoreDtos(Library library);
}