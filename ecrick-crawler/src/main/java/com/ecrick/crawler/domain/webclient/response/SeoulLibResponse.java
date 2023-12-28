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
public class SeoulLibResponse implements LibraryResponse {

    private Integer totalPage;
    private Integer totalCount;
    @JsonProperty("ContentDataList")
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
        @JsonProperty("publishDate")
        private String publicDate;
        @JsonProperty("coverUrl")
        private String coverUrl;
        @JsonProperty("contentsInfo")
        private String contentsInfo;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("ownerCode")
        private String vendor;

        private LocalDate getPublicDate() {
            return LocalDate.parse(publicDate);
        }
    }

    private String getCategory(Library library) {
        return library.getName()
                .replaceAll("서울도서관\\(", "")
                .replaceAll("\\)$", "");
    }

    @Override
    public Integer getTotalBooks() {
        return totalCount;
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
                                .category(getCategory(library))
                                .vendor(content.getVendor())
                                .build())
                .collect(Collectors.toList());
    }

}
