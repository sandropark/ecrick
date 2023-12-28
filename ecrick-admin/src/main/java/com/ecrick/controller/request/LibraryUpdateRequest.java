package com.ecrick.controller.request;

import com.ecrick.common.ContentType;
import com.ecrick.entity.Library;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryUpdateRequest {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Integer size;

    public Library toModel() {
        return Library.builder()
                .id(id)
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .savedBooks(savedBooks)
                .contentType(contentType)
                .requestSize(size)
                .build();
    }
}
