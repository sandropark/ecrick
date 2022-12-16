package com.elib.web.dto;

import com.elib.domain.Library;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryAddFormDto {
    private String name;
    private String url;
    private String apiUrl;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;

    private LibraryAddFormDto() {}

    public static LibraryAddFormDto of() {
        return new LibraryAddFormDto();
    }

    public static LibraryAddFormDto of(String name) {
        return new LibraryAddFormDto(name, null, null, null, null, null);
    }

    public Library toEntity() {
        return Library.of(name, url, apiUrl, totalBooks, savedBooks, contentType);
    }
}
