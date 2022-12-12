package sandro.elib.elib.crwaler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import sandro.elib.elib.crwaler.dto.JsonDto;
import sandro.elib.elib.domain.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrawlUtil {
    private static final String userAgent =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15";

    public static Connection.Response requestUrlAndGetResponse(Library library) throws IOException {
        return Jsoup.connect(library.getApiUrl())
                .userAgent(userAgent)
                .referrer(library.getReferrer())
                .ignoreContentType(true)
                .execute();
    }

    public static Connection.Response requestDetailUrlAndGetResponse(String url, Library library) throws IOException {
        return Jsoup.connect(url)
                .userAgent(userAgent)
                .timeout(100000)
                .referrer(library.getReferrer())
                .ignoreContentType(true)
                .execute();
    }

    public static JsonDto getJsonDto(Connection.Response response) throws JsonProcessingException {   // TODO : 파싱타입 추가시 수정
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), JsonDto.class);
    }

    public static List<String> getDetailUrls(Integer totalBooks, String apiUrl) {  // TODO : 파싱타입 추가시 수정
        ArrayList<String> detailUrls = new ArrayList<>();
        int size = 40;
        int maxPage = (totalBooks / size) + 2;
        for (int page = 1; page < maxPage; page++) {
            detailUrls.add(apiUrl + "&pageIndex=" + page +"&recordCount=" + size);
        }
        return detailUrls;
    }


}
