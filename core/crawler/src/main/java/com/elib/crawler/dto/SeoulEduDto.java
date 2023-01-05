package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        return String.valueOf(meta.select("span")
                .get(6)
                .text());
    }

    private String getCategory(Element meta) {
        return String.valueOf(meta.select("span")
                .get(4)
                .text());
    }

    private LocalDate getPublicDate(Element infoElib) {
        String publicDate = String.valueOf(infoElib
                        .select("span")
                        .last()
                        .text())
                .strip();

        if (publicDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
            publicDate = publicDate.replaceAll("\\.", "-");
        }
        if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(publicDate);
        }
        if (publicDate.matches("\\d{8}")) {
            return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
        }
        return null;
    }

    private String getPublisher(Element infoElib) {
        return String.valueOf(infoElib
                        .select("span")
                        .get(2)
                        .text())
                .strip();
    }

    private String getAuthor(Element infoElib) {
        return String.valueOf(infoElib
                        .selectFirst("span")
                        .text())
                .strip()
                .replaceAll("[<>]", "")
                .replaceAll(" 글", "")
                .replaceAll(" 외", "")
                .replaceAll(" 글,?그림", "")
                .replaceAll(" 편?공?등?저$", "")
                .replaceAll(" 공?저$", "")
                .strip();
    }

    private String getTitle(Element flexBox) {
        return String.valueOf(flexBox.select("a")
                        .last()
                        .selectFirst("b")
                        .text())
                .strip();
    }

    private String getCoverUrl(Element li) {
        return String.valueOf(li.selectFirst(".thumb > a > img")
                        .attr("src"))
                .strip();
    }

}
