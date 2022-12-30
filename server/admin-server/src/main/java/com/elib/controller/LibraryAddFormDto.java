package com.elib.controller;

import com.elib.dto.LibraryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class LibraryAddFormDto {
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;
    private String service;

    public LibraryDto toLibraryDto() {
        return LibraryDto.builder()
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .service(service)
                .build();
    }

}
