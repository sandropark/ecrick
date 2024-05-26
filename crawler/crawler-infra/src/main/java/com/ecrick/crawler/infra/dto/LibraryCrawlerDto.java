package com.ecrick.crawler.infra.dto;

import com.ecrick.domain.dto.VendorDto;
import jdk.jfr.ContentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
            detailUrls.add(url + "currentPage=" + page);
        }
        detailUrls.add(url + "currentPage=" + totalBooks);
        return detailUrls;
    }
}
