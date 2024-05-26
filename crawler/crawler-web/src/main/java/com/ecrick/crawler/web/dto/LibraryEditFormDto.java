package com.ecrick.crawler.web.dto;

import com.ecrick.domain.dto.LibraryDto;
import com.ecrick.domain.entity.ContentType;
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
