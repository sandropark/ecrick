package com.elib.dto;

import com.elib.domain.Core;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class CoreDetailDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;
    private List<LibraryEbookServiceDto> location;

    private CoreDetailDto(Long id, String title, String author, String publisher, LocalDate publicDate, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicDate = publicDate;
        this.coverUrl = coverUrl;
    }

    public static CoreDetailDto from(Core entity) {
        return new CoreDetailDto(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthor(),
            entity.getPublisher(),
            entity.getPublicDate(),
            entity.getCoverUrl()
        );
    }

}
