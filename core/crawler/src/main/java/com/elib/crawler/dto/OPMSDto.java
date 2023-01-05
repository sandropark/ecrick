package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class OPMSDto implements ResponseDto {

    @JsonProperty("data")
    private List<Content> contents;
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

        private String getField(String str) {
            return String.valueOf(str).strip();
        }
        private String getTitle() {
            return getField(title);
        }

        private String getAuthor() {
            return getField(author);
        }

        private String getPublisher() {
            return getField(publisher);
        }

        private LocalDate getPublicDate() {
            String publicDate = getField(this.publicDate);

            if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(publicDate);
            }
            if (publicDate.matches("\\d{8}")) {
                return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
            }
            return null;
        }

        private String getCoverUrl() {
            return getField(coverUrl);
        }

        private String getBookDescription() {
            return getField(bookDescription);
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
