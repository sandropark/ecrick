package com.ecrick.dto;

import com.ecrick.domain.ContentType;
import com.ecrick.domain.Library;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class LibraryCrawlerDto {
    private Long id;
    private String name;
    private String url;
    private String param;
    private Integer totalBooks;
    private VendorDto vendor;
    private ContentType contentType;
    private Integer size;

    @QueryProjection
    public LibraryCrawlerDto(Long id, String name, String url, String param, Integer totalBooks, VendorDto vendor, ContentType contentType, Integer size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.param = param;
        this.totalBooks = totalBooks;
        this.vendor = vendor;
        this.contentType = contentType;
        this.size = size;
    }

    public static LibraryCrawlerDto from(Library entity) {
        return LibraryCrawlerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .param(entity.getParam())
                .totalBooks(entity.getTotalBooks())
                .vendor(VendorDto.from(entity.getVendor()))
                .contentType(entity.getContentType())
                .size(entity.getSize())
                .build();
    }

    public Library toEntity() {
        return Library.builder()
                .id(id)
                .name(name)
                .url(url)
                .param(param)
                .totalBooks(totalBooks)
                .vendor(vendor.toEntity())
                .contentType(contentType)
                .size(size)
                .build();
    }

    public List<String> getDetailUrls() {
        if (vendor.isAladin()) {
            return getAladinDetailUrls();
        }

        if (param.contains("&")) {
            return getPageAndSizeDetailUrls();
        }

        return getPageDetailUrls();
    }

    private List<String> getPageDetailUrls() {
        List<String> detailUrls = new ArrayList<>();
        int maxPage = (totalBooks / size) + 2;

        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(url + param + page);
        }

        return detailUrls;
    }

    private List<String> getPageAndSizeDetailUrls() {
        List<String> detailUrls = new ArrayList<>();
        int maxPage = (totalBooks / size) + 2;

        String[] params = param.split("&");
        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(url + params[0] + page + "&" + params[1] + size);
        }
        return detailUrls;
    }

    private List<String> getAladinDetailUrls() {
        ArrayList<String> detailUrls = new ArrayList<>();
        for (int page = 1; page < totalBooks; page += size) {
            detailUrls.add(url + param + page);
        }
        detailUrls.add(url + param + totalBooks);
        return detailUrls;
    }

    public Boolean isKyoboXml() {
        return vendor.isKyobo() && contentType.isXml();
    }

    public Boolean isKyoboJson() {
        return vendor.isKyobo() && contentType.isJson();
    }

    public Boolean isYes24Xml() {
        return vendor.isYes24() && contentType.isXml();
    }

    public Boolean isYes24Json() {
        return vendor.isYes24() && contentType.isJson();
    }

    public Boolean isBookcube() {
        return vendor.isBookcube() && contentType.isJson();
    }

    public Boolean isOPMS() {
        return vendor.isOPMS() && contentType.isJson();
    }

    public Boolean isAladin() {
        return vendor.isAladin() && contentType.isXml();
    }

    public Boolean isSeoulLib() {
        return vendor.isSeoulLib();
    }

    public Boolean isSeoulEdu() {
        return vendor.isSeoulEdu();
    }

    public String getKey() {
        return vendor.getName() + contentType;
    }
}
