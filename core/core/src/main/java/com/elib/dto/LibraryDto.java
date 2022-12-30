package com.elib.dto;

import com.elib.domain.Library;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LibraryDto {

    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;
    private String service;

    @Builder
    public LibraryDto(Long id, String name, String url, String param, Integer totalBooks, Integer savedBooks, String contentType, String service) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.param = param;
        this.totalBooks = totalBooks;
        this.savedBooks = savedBooks;
        this.contentType = contentType;
        this.service = service;
    }

    public static LibraryDto from(Library entity) {
        return LibraryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .param(entity.getParam())
                .totalBooks(entity.getTotalBooks())
                .savedBooks(entity.getSavedBooks())
                .contentType(entity.getContentType())
                .service(entity.getService())
                .build();
    }

    public Library toEntity() {
        return Library.builder()
                .id(id)
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
