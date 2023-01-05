package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Yes24JsonDto implements ResponseDto {
    private Integer count;
    @JsonProperty("eBookInfoList")
    private List<Content> contents;

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

        private String getField(String string) {
            return String.valueOf(string).strip();
        }

        private String getTitle() {
            return getField(title);
        }

        private String getAuthor() {
            return getField(author)
                    .replaceAll("[<>]", "")
                    .replaceAll(" 글", "")
                    .replaceAll(" 외", "")
                    .replaceAll(" 글,?그림", "")
                    .replaceAll(" 편?공?등?저$", "")
                    .replaceAll(" 공?저$", "")
                    .strip();
        }

        private String getPublisher() {
            return getField(publisher);
        }

        private LocalDate getPublicDate() {
            String publicDate = getField(this.publicDate);

            try {
                if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return LocalDate.parse(publicDate);
                }
                if (publicDate.matches("\\d{8}")) {
                    return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
                }
            } catch (
            DateTimeParseException e) {
                return null;
            }
            return null;
        }

        private String getCoverUrl() {
            return getField(coverUrl);
        }
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
