package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "channel")
public class KyoboXmlDto implements ResponseDto {

    @XmlElement(name = "listCount")
    private Integer totalCount;

    @XmlElement(name = "list")
    private List<Item> items;

    @Getter
    private static class Item {
        @XmlElement(name = "item")
        private Content content;

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
    public List<BookDto> toBookDtos(Library library) {
        List<BookDto> dtos = new ArrayList<>();

        for (Item item : items) {
            Item.Content content = item.getContent();
            dtos.add(BookDto.builder()
                    .library(library)
                    .title(content.getTitle())
                    .author(content.getAuthor())
                    .publisher(content.getPublisher())
                    .coverUrl(content.getCoverUrl())
                    .vendor(content.getVendor())
                    .category(content.getCategory())
                    .build());
        }

        return dtos;
    }

}
