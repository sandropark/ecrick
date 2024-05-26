package com.ecrick.crawler.infra.dto;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "channel")
public class KyoboXmlDto implements ResponseDto {

    @XmlElement(name = "listCount")
    private Integer totalCount;

    @XmlElement(name = "list")
    private List<Item> items;

    private static class Item {
        @XmlElement(name = "item")
        private Content content;

        private String getTitle() {
            return content.getTitle();
        }

        private String getAuthor() {
            return content.getAuthor();
        }

        private String getPublisher() {
            return content.getPublisher();
        }

        private String getVendor() {
            return content.getVendor();
        }

        private String getCategory() {
            return content.getCategory();
        }

        private String getCoverUrl() {
            return content.getCoverUrl();
        }

        @Getter
        private static class Content {
            @XmlElement(name = "product_nm_kr")
            private String title;
            @XmlElement(name = "text_author_nm")
            private String author;
            @XmlElement(name = "cp_nm1")
            private String publisher;
            @XmlElement(name = "com_nm")
            private String vendor;
            @XmlElement(name = "category_nm")
            private String category;
            @XmlElement(name = "image")
            private String coverUrl;

            private String getCoverUrl() {
                return coverUrl.split("\"")[1].strip();
            }
        }
    }

    @Override
    public Integer getTotalBooks() {
        return totalCount + 20;
    }

    @Override
    public List<CoreDto> toCoreDtos(Library library) {
        List<CoreDto> dtos = new ArrayList<>();
        for (Item item : items) {
            dtos.add(CoreDto.builder()
                    .library(library)
                    .title(item.getTitle())
                    .author(item.getAuthor())
                    .publisher(item.getPublisher())
                    .coverUrl(item.getCoverUrl())
                    .vendor(item.getVendor())
                    .category(item.getCategory())
                    .build());
        }
        return dtos;
    }

}
