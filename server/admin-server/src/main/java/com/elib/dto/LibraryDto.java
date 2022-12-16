package com.elib.dto;

import com.elib.domain.Library;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LibraryDto {

    private Long id;
    private String name;
    private String url;
    private String apiUrl;
    private Integer totalBooks;
    private Integer savedBooks;
    private String contentType;

    private LibraryDto() {}

    public static LibraryDto of() {
        return new LibraryDto();
    }

    public static LibraryDto from(Library library) {
        return new LibraryDto(
                library.getId(),
                library.getName(),
                library.getUrl(),
                library.getApiUrl(),
                library.getTotalBooks(),
                library.getSavedBooks(),
                library.getContentType()
        );
    }

    public Library toEntity() {
        return Library.of(id, name, url, apiUrl, totalBooks, savedBooks, contentType);
    }

}
