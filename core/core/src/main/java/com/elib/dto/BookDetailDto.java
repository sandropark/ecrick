package com.elib.dto;

import com.elib.domain.Book;
import com.elib.domain.Core;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDetailDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private List<LocationDto> location;

    public static BookDetailDto from(Book entity) {
        List<LocationDto> location = new ArrayList<>();
        List<Core> cores = entity.getCores();
        for (Core core : cores) {
            location.add(LocationDto.from(core));
        }
        return new BookDetailDto(entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getPublicDate(),
                entity.getCoverUrl(),
                location
        );
    }

}
