package sandro.elib.elib.crwaler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import sandro.elib.elib.crwaler.dto.JsonDto;
import sandro.elib.elib.crwaler.dto.ResponseDto;
import sandro.elib.elib.crwaler.dto.XmlDto;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.dto.BookDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.jsoup.Connection.Response;

@Slf4j
@Component
public class Crawler {

    public List<BookDto> crawlByLibrary(Library library) {
        int totalBooks;

        try {
            Response response = requestUrlAndGetResponse(library.getApiUrl());
            ResponseDto responseDto = getResponseDto(response);
            totalBooks = responseDto.getTotalBooks();
        } catch (JsonProcessingException | JAXBException e) {
            log.warn("dto 변환 실패 - response를 확인하세요. : 도서관 = {}, url = {}, apiUrl = {}", library.getName(), library.getUrl(), library.getApiUrl());
            return List.of();
        } catch(IOException e) {
            log.warn("크롤링 실패 - url을 확인하세요. : 도서관 = {}, url = {}, apiUrl = {}", library.getName(), library.getUrl(), library.getApiUrl());
            return List.of();
        }
        List<String> detailUrls = getDetailUrls(library, totalBooks);
        List<BookDto> crawledBooks = getBooks(detailUrls);

        log.info("도서관 : {}, 결과 : {}, 총 도서수 : {}, 가져온 도서 수 : {}",
                library.getName(), (totalBooks <= crawledBooks.size() ? "성공" : "실패"), totalBooks, crawledBooks.size());

        return crawledBooks;

    }

    public Response requestUrlAndGetResponse(String url) throws IOException {
        final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15";

        return Jsoup.connect(url)
                .userAgent(userAgent)
                .referrer(url)
                .ignoreContentType(true)
                .execute();
    }

    public ResponseDto getResponseDto(Response response) throws JsonProcessingException, JAXBException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Unmarshaller unmarshaller = JAXBContext.newInstance(XmlDto.class).createUnmarshaller();

        if (response.contentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            return objectMapper.readValue(response.body(), JsonDto.class);
        }
        return (XmlDto) unmarshaller.unmarshal(new StringReader(response.body()));
    }

    public List<String> getDetailUrls(Library library, Integer totalBooks) {
        ArrayList<String> detailUrls = new ArrayList<>();
        if (library.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            int size = 100;
            int maxPage = (totalBooks / size) + 2;
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(library.getApiUrl() + "&pageIndex=" + page +"&recordCount" + size);
            }
        }
        if (library.getContentType().equals(MediaType.TEXT_XML_VALUE)) {
            int size = 20;
            int maxPage = (totalBooks / size) + 2;
            for (int page = 1; page < maxPage; page++) {
                detailUrls.add(library.getApiUrl() + "paging=" + page);
            }
        }
        return detailUrls;
    }

    public List<BookDto> getBooks(List<String> detailUrls) {
        List<BookDto> books = new ArrayList<>();
        detailUrls.forEach(url -> {
            try {
                ResponseDto responseDto = getResponseDto(requestUrlAndGetResponse(url));
                List<BookDto> bookDtos = responseDto.toBookDto();
                books.addAll(bookDtos);
            } catch (JsonProcessingException | JAXBException e) {
                log.warn("dto 변환 실패 - response를 확인하세요. : url = {}, {}", url, e.getLocalizedMessage());
            } catch(IOException e) {
                log.warn("크롤링 실패 - url을 확인하세요. : url = {}, {}", url, e.getLocalizedMessage());
            }
        });
        return books;
    }

    public String getApiUrl(Library library) {
        String url = library.getUrl().toLowerCase();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length()-1);
        }
        if (url.endsWith(".co.kr") || url.endsWith("elibrary-front")) {
            return url + "/content/contentListMobile.json?cttsDvsnCode=001";
        }
        if (url.endsWith("kyobo_t3_mobile")) {
            return url + "/Tablet/Main/Ebook_MoreView.asp?";
        }
        return null;
    }

    public String getReferrer(Library library) {
        String url = library.getUrl().toLowerCase();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length()-1);
        }
        if (url.endsWith(".co.kr") || url.endsWith("elibrary-front")) {
            return url + "/content/contentList.ink?cttsDvsnCode=001";
        }
        if (url.endsWith("kyobo_t3_mobile")) {
            return url + "/Kyobo_T3_Mobile/Tablet/Main/Ebook_List.asp?";
        }
        return null;
    }

}