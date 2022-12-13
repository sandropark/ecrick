package sandro.elib.elib.crawler.dto;

import lombok.Getter;
import sandro.elib.elib.dto.BookDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@XmlRootElement(name = "channel")
public class XmlDto implements ResponseDto {

    @XmlElement(name = "listCount")
    private Integer totalCount;

    @XmlElement(name = "list")
    private List<Item> contents;

    @Override
    public Integer getTotalBooks() {
        return totalCount + 20;
    }

    @Override
    public List<BookDto> toBookDto() { // TODO : 메시지를 보내는 방식으로 개선
        if (contents == null) {
            return List.of();
        }
        return contents.stream()
                .map(Item::getContent)
                .map(content -> BookDto.of(
                        content.getTitle(),
                        content.getAuthor(),
                        content.getPublisher(),
                        null,
                        content.parseUrl()))
                .collect(Collectors.toList());
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
