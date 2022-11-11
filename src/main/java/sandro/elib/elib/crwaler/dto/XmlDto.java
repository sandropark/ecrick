package sandro.elib.elib.crwaler.dto;

import lombok.Data;
import sandro.elib.elib.dto.BookDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "channel")
public class XmlDto implements ResponseDto {

    @XmlElement(name = "listCount")
    private Integer totalCount;

    @XmlElement(name = "list")
    private List<ContentWrap> contents;

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
                .map(ContentWrap::getContent)
                .map(content -> BookDto.of(
                        content.getTitle(),
                        content.getAuthor(),
                        content.getPublisher(),
                        null,
                        content.parseUrl()))
                .collect(Collectors.toList());
    }

}

@Data
@XmlAccessorType(XmlAccessType.FIELD)
class ContentWrap {
    @XmlElement(name = "item")
    private Content content;

}

@Data
@XmlAccessorType(XmlAccessType.FIELD)
class Content {

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