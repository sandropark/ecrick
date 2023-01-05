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
import java.util.Map;

@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookcubeDto implements ResponseDto {

    @JsonProperty("totalItems")
    private Integer totalBooks;

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
            String publicDate = String.valueOf(this.publicDate);
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
        return totalBooks;
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
                    .category(content.getCategory())
                    .build());
        }

        return dtos;
    }

    // '0000-00-00' 은 LocalDate.parse 시 예외가 발생한다.
    private LocalDate getPublicDate(String key, Map<String, String> content) {
        String publicDate = getResult(key, content);
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

    private String getResult(String key, Map<String, String> content) {
        return String.valueOf(content.get(key)).strip();
    }

    public List<String> getDetailUrl(String url) {
        ArrayList<String> detailUrls = new ArrayList<>();
        for (int page = 1; page < totalPages + 1; page++) {
            detailUrls.add(url + "page=" + page);
        }
        return detailUrls;
    }

}
