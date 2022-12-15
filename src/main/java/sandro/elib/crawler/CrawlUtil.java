package sandro.elib.crawler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import sandro.elib.crawler.dto.JsonDto;
import sandro.elib.crawler.dto.ResponseDto;
import sandro.elib.crawler.dto.XmlDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

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

    public static Response requestUrl(String url) throws IOException {
        return Jsoup.connect(url)
                .headers(headers)
                .timeout(300000)
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

}
