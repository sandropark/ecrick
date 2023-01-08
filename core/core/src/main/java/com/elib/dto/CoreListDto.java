package com.elib.dto;

import com.elib.domain.Core;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CoreListDto {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publicDate;
    private String coverUrl;

    @QueryProjection
    public CoreListDto(Core core) {
        id = core.getId();
        title = core.getTitle();
        author = core.getAuthor();
        publisher = core.getPublisher();
        publicDate = core.getPublicDate();
        coverUrl = core.getCoverUrl();
    }

}
