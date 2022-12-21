package com.elib.crawler.dto;

import com.elib.dto.BookDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Getter
@Setter
public class JsonDto extends StringUtil implements ResponseDto {
    private String orderByKey;
    private int viewCnt;
    private Map<String, Integer> paging;
    private String contentAll;
    private String listType;
    private List<Map<String, String>> contentList;

    @Override
    public Integer getTotalBooks() {
        return paging.get("total");
    }

    @Override
    public List<BookDto> toBookDto() {
        return contentList.stream()
                .map(content -> BookDto.of(
                    clean(content.get("cttsHnglName")),
                    cleanAuthor(content.get("sntnAuthName")),
                    clean(content.get("pbcmName")),
                    getParsedDate(clean(content.get("publDate"))),
                    clean(content.get("coverImage"))
                )
            ).collect(Collectors.toList());
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

    private LocalDate getParsedDate(String date) {
        return hasText(date) ? LocalDate.parse(date) : null;
    }

}
