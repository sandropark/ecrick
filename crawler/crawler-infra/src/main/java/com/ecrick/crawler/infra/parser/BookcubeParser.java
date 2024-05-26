package com.ecrick.crawler.infra.parser;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.crawler.infra.dto.BookcubeDto;
import com.ecrick.crawler.infra.utils.BodyCleanUtils;
import com.ecrick.crawler.infra.utils.ObjectMapperUtils;
import com.ecrick.domain.entity.Library;
import org.jsoup.Connection;
import org.springframework.stereotype.Component;

@Component
public class BookcubeParser implements CrawlerParser {
    @Override
    public Boolean supports(Library library) {
        return library.isBookcube();
    }

    @Override
    public ResponseDto parse(Connection.Response response) {
        return ObjectMapperUtils.readValue(cleanBody(response.body()), BookcubeDto.class);  // "\N"이 포함되어 있어서 파싱이 안되기 때문에 소문자로 변경해준다.
    }

    private String cleanBody(String body) {
        return BodyCleanUtils.clean(customize(fixError(body)));
    }

    private String fixError(String body) {
        return body
                .replaceAll("N", "n")
                .replaceAll("&gt;.,&lt;", "");
    }

    private String customize(String body) {
        return body
                .replaceAll("(\\r\\n|\\r|\\n|\\n\\r|\\t)", "")
                .replaceFirst("^\\{.+\"result\":\\[", "")
                .replaceFirst("},", ",\"contents\":")
                .replaceFirst("]]}$", "]}");
    }
}
