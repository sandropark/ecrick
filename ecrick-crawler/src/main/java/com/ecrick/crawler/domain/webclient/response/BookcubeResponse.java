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
public class BookcubeResponse implements LibraryResponse {

    @JsonProperty("totalItems")
    private Integer totalBooks;

    @JsonProperty("totalPages")
    private Integer totalPages;

    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        private String title;
        private String author;
        private String publisher;
        @JsonProperty("publish_date")
        private String publicDate;
        @JsonProperty("detail_image")
        private String coverUrl;
        @JsonProperty("supplier_code")
        private String vendor;
        @JsonProperty("categoryMain")
        private String category;

        private LocalDate getPublicDate() {
            return LocalDate.parse(publicDate);
        }
    }

    @Override
    public Integer getTotalBooks() {
        return totalBooks;
    }

    @Override
    public List<RowBook> toRowBooks(Library library) {
        return contents.stream()
                .map(content -> RowBook.builder()
                        .library(library)
                        .title(content.getTitle())
                        .author(content.getAuthor())
                        .publisher(content.getPublisher())
                        .publicDate(content.getPublicDate())
                        .coverUrl(content.getCoverUrl())
                        .vendor(content.getVendor())
                        .category(content.getCategory())
                        .build())
                .collect(Collectors.toList());

    }

}
