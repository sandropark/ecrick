package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KyoboJsonDto implements ResponseDto {
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

        private String getTitle() {
            return String.valueOf(title)
                    .strip()
                    .replaceAll("&quot;", "\"");
        }

        private LocalDate getPublicDate() {
            String publicDate = String.valueOf(this.publicDate).strip();

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
    }

    @Override
    public Integer getTotalBooks() {
        return paging.getTotalBooks();
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
                    .vendor(content.getVendor())
                    .build());
        }

        return dtos;
    }

}
