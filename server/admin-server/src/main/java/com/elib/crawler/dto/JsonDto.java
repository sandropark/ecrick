package com.elib.crawler.dto;

import lombok.Getter;
import lombok.Setter;
import com.elib.dto.BookDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Getter
@Setter
public class JsonDto implements ResponseDto {
    private String orderByKey;
    private int viewCnt;
    private Map<String, Integer> paging;
    private String contentAll;
    private String listType;
    private List<Map<String, Object>> contentList;

    public Integer getTotalBooks() {
        return paging.get("total");
    }

    public List<BookDto> toBookDto() {
        List<String> fieldNames = List.of("cttsHnglName", "sntnAuthName", "pbcmName", "publDate", "coverImage");
        return contentList.stream().map(content -> {
            Map<String, String> values = new HashMap<>();
            content.forEach((k, v) -> {
                if (fieldNames.contains(k)) {
                    values.put(k, String.valueOf(v));
                }
            });
            return BookDto.of(
                    values.get("cttsHnglName"),
                    values.get("sntnAuthName"),
                    values.get("pbcmName"),
                    getParsedDate(values.get("publDate")),
                    values.get("coverImage"));
        }).collect(Collectors.toList());
    }

    private LocalDate getParsedDate(String date) {
        return hasText(date) ? LocalDate.parse(date) : null;
    }

    @Override
    public List<String> getDetailUrl(String apiUrl) {
        ArrayList<String> detailUrls = new ArrayList<>();
        int size = 150;
        int maxPage = (getTotalBooks() / size) + 2;
        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(apiUrl + "&pageIndex=" + page + "&recordCount=" + size);
        }
        return detailUrls;
    }
}
