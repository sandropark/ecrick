package com.elib.dto;

import com.elib.domain.Book;
import com.elib.domain.Library;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BookDto {

    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private String vendor;
    private String category;
    private Library library;
    private String bookDescription;

    public Book toEntity() {
        return Book.builder()
                .library(library)
                .title(title)
                .author(author)
                .publisher(publisher)
                .publicDate(publicDate)
                .coverUrl(coverUrl)
                .vendor(vendor)
                .category(category)
                .build();
    }

}
