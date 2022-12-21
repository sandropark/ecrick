package com.elib.crawler.dto;

import lombok.Getter;
import com.elib.dto.BookDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@XmlRootElement(name = "channel")
public class XmlDto extends StringUtil implements ResponseDto {

    @XmlElement(name = "listCount")
    private Integer totalCount;

    @XmlElement(name = "list")
    private List<Item> contents;

    @Override
    public Integer getTotalBooks() {
        return totalCount + 20;
    }

    @Override
    public List<BookDto> toBookDto() {
        if (contents == null) {
            return List.of();
        }
        return contents.stream()
                .map(Item::getContent)
                .map(content -> BookDto.of(
                        clean(content.getTitle()),
                        cleanAuthor(content.getAuthor()),
                        clean(content.getPublisher()),
                        null,
                        clean(content.parseUrl())))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDetailUrl(String apiUrl) {
        ArrayList<String> detailUrls = new ArrayList<>();
        int size = 20;
        int maxPage = (getTotalBooks() / size) + 2;
        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(apiUrl + "paging=" + page);
        }
        return detailUrls;
    }

    @Getter
    static class Item {

        @XmlElement(name = "item")
        private Content content;
    }
    @Getter
    static class Content {

        @XmlElement(name = "image")
        private String imageUrl;
        @XmlElement(name = "product_nm_kr")
        private String title;
        @XmlElement(name = "text_author_nm")
        private String author;
        @XmlElement(name = "cp_nm1")
        private String publisher;

        public String parseUrl() {
            return imageUrl.split("\"")[1];
        }
    }
}
