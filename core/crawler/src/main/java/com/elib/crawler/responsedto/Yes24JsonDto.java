package com.elib.crawler.responsedto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yes24JsonDto implements ResponseDto {
    private Integer count;
    @JsonProperty("eBookInfoList")
    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        @JsonProperty("pdName")
        private String title;
        @JsonProperty("authorName")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("ebookYmd")
        private String publicDate;
        @JsonProperty("thumbnail")
        private String coverUrl;
    }

    @Override
    public Integer getTotalBooks() {
        return count;
    }

    @Override
    public List<BookDto> toBookDtos(Library library) {
        List<BookDto> dtos = new ArrayList<>();

        for (Content content : contents) {
            dtos.add(BookDto.builder()
                    .library(library)
                    .title(content.getTitle())
                    .author(content.getAuthor())
                    .publisher(content.getPublisher())
                    .publicDate(content.getPublicDate())
                    .coverUrl(content.getCoverUrl())
                    .build());
        }

        return dtos;
    }

}
