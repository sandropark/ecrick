package com.elib.crawler.request;

import com.elib.domain.ContentType;
import com.elib.dto.LibraryDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LibraryUpdateRequestDto {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Integer size;

    public LibraryDto toDto() {
        return LibraryDto.builder()
                .id(id)
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
