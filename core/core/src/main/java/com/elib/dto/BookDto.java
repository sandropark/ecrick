package com.elib.dto;

import com.elib.domain.Book;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDto {

    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String imageUrl;

    public static BookDto of(String title, String author, String publisher, LocalDate publicDate, String imageUrl) {
        return new BookDto(title, author, publisher, publicDate, imageUrl);
    }

    public Book toEntity() {
        return Book.of(title, author, publisher, publicDate, imageUrl);
    }

    public boolean hasPublicDate() {
        return publicDate != null;
    }
}