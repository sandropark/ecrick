package com.ecrick.model;

import com.ecrick.entity.RowBook;
import com.ecrick.service.CrawlerModel;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class SeoulEduCrawlerModel extends CommonModel implements CrawlerModel {
    private static final Pattern TOTAL_BOOKS_PATTERN = Pattern.compile("분류에 <span>(\\d+)</span> 개의");
    private static final Pattern TITLE_PATTERN = Pattern.compile("<b>(.+)</b>");
    private static final Pattern COVER_URL_PATTERN = Pattern.compile("src=\"(.+)\"alt=");
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("카테고리:</label><span>(.+)</span><span class=\"txt-bar\"></span><label>유통사");
    private static final Pattern VENDOR_PATTERN = Pattern.compile("유통사:</label><span>(.+)</span><span class=\"txt-bar\"></span><label>대출가능여부");
    private static final Pattern INFO_PATTERN = Pattern.compile("<div class=\"info-elib\"><span>(.+)</span></div><span class=\"snipet\">");

    private final String body;

    @Override
    public Integer getTotalBooks() {
        Matcher matcher = TOTAL_BOOKS_PATTERN.matcher(body);
        if (matcher.find())
            return Integer.valueOf(matcher.group(1));
        return null;
    }

    @Override
    public List<RowBook> getRowBooks() {
        String[] list = getList();
        return Arrays.stream(list).map(data ->
                RowBook.builder()
                        .library(library)
                        .title(getTitle(data))
                        .author(getAuthor(data))
                        .publisher(getPublisher(data))
                        .publicDate(getPublicDate(data))
                        .coverUrl(getCoverUrl(data))
                        .category(getCategory(data))
                        .vendor(getVendor(data))
                        .build()
        ).toList();
    }

    private String[] getList() {
        String list = body.substring(
                        body.indexOf("<ul class=\"bbs_webzine elib\">"),
                        body.indexOf("<div class=\"dataTables_paginate\">")
                ).replaceAll("[\t\r(\n)]", "")
                .replaceAll("\\s{2,}", "");
        String[] split = list.split("</li>");
        return Arrays.copyOfRange(split, 0, split.length - 2);
    }

    private String getTitle(String data) {
        Matcher m = TITLE_PATTERN.matcher(data);
        if (m.find())
            return m.group(1);
        return null;
    }

    private String getAuthor(String data) {
        return getInfo(data)[0];
    }

    private String getPublisher(String data) {
        return getInfo(data)[2];
    }

    private LocalDate getPublicDate(String data) {
        return LocalDate.parse(getInfo(data)[4].replaceAll("\\.", ""), DateTimeFormatter.BASIC_ISO_DATE);
    }

    private String getCoverUrl(String data) {
        Matcher matcher = COVER_URL_PATTERN.matcher(data);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    private String getCategory(String data) {
        Matcher matcher = CATEGORY_PATTERN.matcher(data);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    private String getVendor(String data) {
        Matcher matcher = VENDOR_PATTERN.matcher(data);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    private String[] getInfo(String data) {
        Matcher matcher = INFO_PATTERN.matcher(data);
        if (matcher.find()) {
            return matcher.group(1)
                    .replaceAll("<span>", "")
                    .split("</span>");
        }
        return null;
    }
}
