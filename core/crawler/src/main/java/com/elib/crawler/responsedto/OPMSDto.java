package com.elib.crawler.responsedto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OPMSDto implements ResponseDto {

    @JsonProperty("data")
    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("pubdate")
        private String publicDate;
        @JsonProperty("cover")
        private String coverUrl;
        @JsonProperty("description")
        private String bookDescription;
    }

    @JsonProperty("meta")
    private Meta meta;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Meta {
        @JsonProperty("count")
        private Integer totalBooks;
    }

    @Override
    public Integer getTotalBooks() {
        return meta.getTotalBooks();
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
