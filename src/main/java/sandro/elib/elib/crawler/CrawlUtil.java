package sandro.elib.elib.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import sandro.elib.elib.crawler.dto.JsonDto;
import sandro.elib.elib.crawler.dto.ResponseDto;
import sandro.elib.elib.crawler.dto.XmlDto;
import sandro.elib.elib.domain.Library;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.jsoup.Connection.Response;
import static org.springframework.http.MediaType.TEXT_XML_VALUE;

public class CrawlUtil {
    private static final HashMap<String, String> headers = new HashMap<>() {{
        put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        put("Accept-Encoding", "gzip, deflate");
        put("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7");
        put("Cache-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
        put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");
    }};

    public static Response requestUrlAndGetResponse(Library library) throws IOException {
        return Jsoup.connect(library.getApiUrl())
                .headers(headers)
                .referrer(library.getReferrer())
                .ignoreContentType(true)
                .execute();
    }

    public static Response requestDetailUrlAndGetResponse(String url, Library library) throws IOException {
        return Jsoup.connect(url)
                .headers(headers)
                .referrer(library.getReferrer())
                .ignoreContentType(true)
                .execute();
    }

    public static ResponseDto responseToDto(Response response) throws JsonProcessingException, JAXBException {
        if (response.contentType().contains(TEXT_XML_VALUE)) {
            Unmarshaller unmarshaller = JAXBContext.newInstance(XmlDto.class).createUnmarshaller();
            return (ResponseDto) unmarshaller.unmarshal(new StringReader(response.body()));
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), JsonDto.class);
        }
    }

    public static List<String> getDetailUrls(ResponseDto dto, String apiUrl) {
        ArrayList<String> detailUrls = new ArrayList<>();
        if (dto instanceof XmlDto) {
            int size = 20;
            int maxPage = (dto.getTotalBooks() / size) + 2;
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(apiUrl + "paging=" + page);
            }
        } else {
            int size = 40;
            int maxPage = (dto.getTotalBooks() / size) + 2;
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(apiUrl + "&pageIndex=" + page + "&recordCount=" + size);
            }
        }
        return detailUrls;
    }


}
