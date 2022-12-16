package com.elib.dto;

import com.elib.domain.Relation;
import lombok.Getter;

@Getter
public class LibraryEbookServiceDto {
    private final String library;
    private final String ebookService;

    private LibraryEbookServiceDto(String library, String ebookService) {
        this.library = library;
        this.ebookService = ebookService;
    }

    public static LibraryEbookServiceDto from(Relation relation) {
        return new LibraryEbookServiceDto(relation.getLibrary().getName(), relation.getEbookService().getName());
    }
}
