package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeoulLibDto implements ResponseDto {

    private Integer totalPage;
    private Integer totalCount;
    @JsonProperty("ContentDataList")
    private List<Content> contents;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Content {
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("publishDate")
        private String publishDate;
        @JsonProperty("coverUrl")
        private String coverUrl;
        @JsonProperty("contentsInfo")
        private String contentsInfo;
        @JsonProperty("isbn")
        private String isbn;


        private String getResult(String field) {
            return String.valueOf(field).strip();
        }

        private String getTitle() {
            return getResult(title);
        }

        private String getAuthor() {
            return getResult(author)
                    .replaceAll("[<>]", "")
                    .replaceAll(" 글", "")
                    .replaceAll(" 외", "")
                    .replaceAll(" 글,?그림", "")
                    .replaceAll(" 편?공?등?저$", "")
                    .replaceAll(" 공?저$", "")
                    .replaceAll(" 지음$", "")
                    .strip();
        }

        private String getPublisher() {
            return getResult(publisher);
        }

        private LocalDate getPublishDate() {
            String publicDate = getResult(publishDate);
            try {
                if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return LocalDate.parse(publicDate);
                }
                if (publicDate.matches("\\d{8}")) {
                    return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
                }
            } catch (DateTimeParseException e) {
                return null;
            }
            return null;
        }

        private String getCoverUrl() {
            return getResult(coverUrl);
        }

        private String getContentsInfo() {
            return getResult(contentsInfo);
        }
    }

    @Override
    public Integer getTotalBooks() {
        return totalCount;
    }

    @Override
    public List<BookDto> toBookDtos(Library library) {
        return contents.stream()
                .map(content -> BookDto.builder()
                        .library(library)
                        .title(content.getTitle())
                        .author(content.getAuthor())
                        .publisher(content.getPublisher())
                        .publicDate(content.getPublishDate())
                        .coverUrl(content.getCoverUrl())
                        .category(getCategory(library))
                        .build())
                .collect(Collectors.toList());
    }

    private String getCategory(Library library) {
        return library.getName()
                .replaceAll("서울도서관\\(", "")
                .replaceAll("\\)$", "");
    }

}
