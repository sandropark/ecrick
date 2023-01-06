package com.elib.dto;

import com.elib.domain.Book;
import com.elib.domain.Library;
import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BookDto {

    private String title;
    private String author;
    private String publisher;
    private String publicDate;
    private String coverUrl;
    private String vendor;
    private String category;
    private Library library;
    private String bookDescription;

    private boolean HasPublicDate() {
        return StringUtils.hasText(publicDate) && validationPublicDate();
    }

    private boolean validationPublicDate() {
        try {
            getDate();
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private LocalDate getDate() {
        return LocalDate.parse(
                publicDate.replaceAll("[^0-9]", ""),
                DateTimeFormatter.BASIC_ISO_DATE
        );
    }

    public Book toEntity() {
        Book.BookBuilder builder = Book.builder()
                .library(library)
                .title(
                        title
                        .replaceAll("&quot;", "\"")
                        .strip()
                )
                .coverUrl(coverUrl)
                .category(category);

        // null을 넣으면 Builder.default가 동작하지 않는다.
        if (StringUtils.hasText(author)) {
            builder.author(
                    author
                    .strip()
                    .replaceAll("[<>]", "")
                    .replaceAll(" 편?공?등?저$", "")
                    .replaceAll(" 공?저$", "")
                    .replaceAll(" 지음$", "")
                    .replaceAll(" 외$", "")
                    .replaceAll("&#49804;", "슌")
                    .strip()
            );
        }

        if (StringUtils.hasText(publisher)) {
            builder.publisher(publisher.strip());
        }

        if (HasPublicDate()) {
            builder.publicDate(getDate());
        }

        if (StringUtils.hasText(vendor)) {
            builder.vendor(vendor.strip());
        }

        return builder.build();
    }

}
