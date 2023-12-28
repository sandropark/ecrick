package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SeoulEduResponse implements LibraryResponse {
    private final Document document;

    @Override
    public Integer getTotalBooks() {
        return Integer.valueOf(document.select("#contentArea > .elib_top > .sub001 > span")
                .last()
                .text());
    }

    @Override
    public List<RowBook> toRowBooks(Library library) {
        return document.select("#contentArea > .elib > li").stream()
                .map(li -> {
                    Element flexBox = li.selectFirst(".list-body > .flexbox");
                    Element infoElib = flexBox.selectFirst(".info-elib");
                    Element meta = li.selectFirst(".list-body > .meta");
                    return RowBook.builder()
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

    private LocalDate getPublicDate(Element infoElib) {
        String publicDate = infoElib.select("span").last().text();
        if (publicDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}"))
            publicDate = publicDate.replaceAll("\\.", "");
        return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
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
