package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "channel")
public class KyoboXmlResponse implements LibraryResponse {

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
    public List<RowBook> toRowBooks(Library library) {
        return items.stream().map(item ->
                        RowBook.builder()
                                .library(library)
                                .title(item.getTitle())
                                .author(item.getAuthor())
                                .publisher(item.getPublisher())
                                .coverUrl(item.getCoverUrl())
                                .vendor(item.getVendor())
                                .category(item.getCategory())
                                .build())
                .collect(Collectors.toList());
    }

}
