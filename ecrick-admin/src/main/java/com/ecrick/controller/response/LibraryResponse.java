package com.ecrick.controller.response;

import com.ecrick.common.ContentType;
import com.ecrick.entity.Library;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryResponse {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private Integer savedBooks;
    private ContentType contentType;
    private String vendor;
    private Integer size;

    public static LibraryResponse from(Library library) {
        return new LibraryResponse(
                library.getId(),
                library.getName(),
                library.getUrl(),
                library.getParam(),
                library.getTotalBooks(),
                library.getSavedBooks(),
                library.getContentType(),
                library.getVendorName(),
                library.getRequestSize()
        );
    }
}
