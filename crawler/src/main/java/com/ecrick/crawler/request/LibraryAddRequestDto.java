package com.ecrick.crawler.request;

import com.ecrick.domain.ContentType;
import com.ecrick.dto.LibraryDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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