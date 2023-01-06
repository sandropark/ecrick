package com.elib.crawler.responsedto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class SeoulEduDto implements ResponseDto {
    private Document document;

    public SeoulEduDto init(Document document) {
        this.document = document;
        return this;
    }

    @Override
    public Integer getTotalBooks() {
        return Integer.valueOf(document.select("#contentArea > .elib_top > .sub001 > span")
                .last()
                .text());
    }

    @Override
    public List<BookDto> toBookDtos(Library library) {
        Elements lis = document.select("#contentArea > .elib > li");
        return lis.stream().map(li -> {
            Element flexBox = li.selectFirst(".list-body > .flexbox");
            Element infoElib = flexBox.selectFirst(".info-elib");
            Element meta = li.selectFirst(".list-body > .meta");
            return BookDto.builder()
                    .library(library)
                    .title(getTitle(flexBox))
                    .author(getAuthor(infoElib))
                    .publisher(getPublisher(infoElib))
                    .publicDate(getPublicDate(infoElib))
                    .coverUrl(getCoverUrl(li))
                    .category(getCategory(meta))
                    .vendor(getVendor(meta))
                    .build();
        }).collect(Collectors.toList());
    }

    private String getVendor(Element meta) {
        return meta.select("span")
                .get(6)
                .text();
    }

    private String getCategory(Element meta) {
        return meta.select("span")
                .get(4)
                .text();
    }

    private String getPublicDate(Element infoElib) {
        return infoElib.select("span").last().text();
    }

    private String getPublisher(Element infoElib) {
        return infoElib
                .select("span")
                .get(2)
                .text();
    }

    private String getAuthor(Element infoElib) {
        return infoElib.selectFirst("span").text();
    }

    private String getTitle(Element flexBox) {
        return flexBox.select("a")
                .last()
                .selectFirst("b")
                .text();
    }

    private String getCoverUrl(Element li) {
        return li.selectFirst(".thumb > a > img")
                .attr("src");
    }

}
