package com.ecrick.crawler.web.dto;

import com.ecrick.domain.dto.LibraryDto;
import com.ecrick.domain.entity.ContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryAddRequestDto {
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Integer size;

    public LibraryDto toDto() {
        return LibraryDto.builder()
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .size(size)
                .build();
    }
}
