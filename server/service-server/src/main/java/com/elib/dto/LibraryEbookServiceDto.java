package com.elib.dto;

import lombok.Getter;
import com.elib.domain.Relation;

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
