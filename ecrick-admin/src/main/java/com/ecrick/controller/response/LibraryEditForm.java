package com.ecrick.controller.response;

import com.ecrick.common.ContentType;
import com.ecrick.entity.Library;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryEditForm {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private Long vendorId;
    private Integer size;

    public static LibraryEditForm from(Library dto) {
        return new LibraryEditForm(dto.getId(),
                dto.getName(),
                dto.getUrl(),
                dto.getParam(),
                dto.getTotalBooks(),
                dto.getSavedBooks(),
                dto.getContentType(),
                dto.getVendor().getId(),
                dto.getRequestSize()
        );

    }
}
