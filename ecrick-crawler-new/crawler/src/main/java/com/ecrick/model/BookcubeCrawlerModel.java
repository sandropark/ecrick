package com.ecrick.model;

import com.ecrick.entity.RowBook;
import com.ecrick.service.CrawlerModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookcubeCrawlerModel extends CommonModel implements CrawlerModel {

    @Override
    public Integer getTotalBooks() {
        return totalBooks;
    }

    @Override
    public List<RowBook> getRowBooks() {
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
            if (publicDate.startsWith("3")) {
                publicDate = publicDate.replace("3", "2");
            }
            if (publicDate.matches("\\d{8}")) {
                return LocalDate.parse(publicDate, BASIC_ISO_DATE);
            } else if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}"))
                return LocalDate.parse(publicDate);
            else
                return null;
        }
    }

}
