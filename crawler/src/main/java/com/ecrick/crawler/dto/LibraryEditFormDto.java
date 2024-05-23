package com.ecrick.crawler.dto;

import com.ecrick.core.domain.ContentType;
import com.ecrick.core.dto.LibraryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryEditFormDto {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Long vendorId;
    private Integer size;

    public static LibraryEditFormDto from(LibraryDto dto) {
        return new LibraryEditFormDto(dto.getId(),
                dto.getName(),
                dto.getUrl(),
                dto.getParam(),
                dto.getTotalBooks(),
                dto.getSavedBooks(),
                dto.getContentType(),
                dto.getVendor().getId(),
                dto.getSize()
        );

    }
}
