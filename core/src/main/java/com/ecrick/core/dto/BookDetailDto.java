package com.ecrick.core.dto;

import com.ecrick.core.domain.Book;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        return new BookDetailDto(entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPublisher(),
                entity.getPublicDate(),
                entity.getCoverUrl(),
                entity.getCores().stream()
                        .map(LocationDto::from)
                        .sorted(Comparator.comparing(LocationDto::getLibraryName))
                        .collect(Collectors.toList())
        );
    }

    public static BookDetailDto empty() {
        return new BookDetailDto(null, null, null, null, null, null, List.of(LocationDto.empty()));
    }
}
