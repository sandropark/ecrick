package com.elib.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookListDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;

    @QueryProjection
    public BookListDto(Long id, String title, String author, String publisher, LocalDate publicDate, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.coverUrl = coverUrl;
    }
}
