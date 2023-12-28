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
public class KyoboJsonResponse implements LibraryResponse {
    private Total paging;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Total {
        @Getter
        @JsonProperty("total")
        private Integer totalBooks;
    }

    @JsonProperty("contentList")
    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        @JsonProperty("cttsHnglName")
        private String title;
        @JsonProperty("sntnAuthName")
        private String author;
        @JsonProperty("pbcmName")
        private String publisher;
        @JsonProperty("publDate")
        private String publicDate;
        @JsonProperty("coverImage")
        private String coverUrl;
        @JsonProperty("entsDvsnName")
        private String vendor;

        private LocalDate getPublicDate() {
            return LocalDate.parse(publicDate);
        }
    }

    @Override
    public Integer getTotalBooks() {
        return paging.getTotalBooks();
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
                                .vendor(content.getVendor())
                                .build())
                .collect(Collectors.toList());
    }

}
