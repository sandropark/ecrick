package com.ecrick.dto;

import com.ecrick.domain.Library;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookcubeDto implements ResponseDto {

    private Integer totalItems;
    private List<Content> contents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Content {
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
    }

    @Override
    public Integer getTotalBooks() {
        return totalItems;
    }

    @Override
    public List<CoreDto> toCoreDtos(Library library) {
        List<CoreDto> dtos = new ArrayList<>();
        for (Content content : contents) {
            dtos.add(CoreDto.builder()
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

}
