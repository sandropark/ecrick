package com.ecrick.crawler.infra.dto;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
    }

    @Override
    public Integer getTotalBooks() {
        return paging.getTotalBooks();
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
                    .build());
        }

        return dtos;
    }

}
