package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OPMSResponse implements LibraryResponse {

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

        private LocalDate getPublicDate() {
            if (publicDate.matches("\\d{8}"))
                return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
            return LocalDate.parse(publicDate);
        }
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
    public List<RowBook> toRowBooks(Library library) {
        return contents.stream().map(content ->
                        RowBook.builder()
                                .library(library)
                                .title(content.getTitle())
                                .author(content.getAuthor())
                                .publisher(content.getPublisher())
                                .publicDate(content.getPublicDate())
                                .coverUrl(content.getCoverUrl())
                                .build())
                .collect(Collectors.toList());
    }

}
