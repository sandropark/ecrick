package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yes24JsonResponse implements LibraryResponse {
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

        private LocalDate getPublicDate() {
            return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
        }
    }

    @Override
    public Integer getTotalBooks() {
        return count;
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
