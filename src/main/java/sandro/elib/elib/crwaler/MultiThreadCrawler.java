package sandro.elib.elib.crwaler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sandro.elib.elib.crwaler.dto.JsonDto;
import sandro.elib.elib.crwaler.dto.ResponseDto;
import sandro.elib.elib.crwaler.dto.XmlDto;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.repository.EBookServiceRepository;
import sandro.elib.elib.repository.LibraryRepository;
import sandro.elib.elib.repository.RelationRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.jsoup.Connection.Response;

@Slf4j
@RequiredArgsConstructor
@Service
public class MultiThreadCrawler implements Runnable {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final sandro.elib.elib.repository.EBookServiceRepository EBookServiceRepository;
    private final RelationRepository relationRepository;
    private final Crawler crawler;

    @Setter private Library library;

    @Override
    public void run() {
        log.info("run! = {}", library.getName());
//        ResponseDto responseDto;
//        try {
//            Response response = requestUrlAndGetResponse(library.getApiUrl());
//            responseDto = getResponseDto(response);
//        } catch (JsonProcessingException | JAXBException e) {
//            log.warn("*[DTO 파싱 실패] 도서관 = {}", library.getName());
//            return;
//        } catch (IOException e) {
//            log.warn("*[예외 발생] 도서관 = {}, {}", library.getName(), e.getLocalizedMessage());
//            return;
//        }
//        Integer totalBooks = responseDto.getTotalBooks();
//        if (Objects.equals(totalBooks, library.getTotalBooks()) && Objects.equals(library.getTotalBooks(), library.getSavedBooks())) {
//            log.info("[작업 종료] 최신 상태입니다. : {}", library.getName());
//            return;
//        }
//        List<String> detailUrls = getDetailUrls(totalBooks);
//        List<BookDto> books = getBooks(detailUrls);
//
//        log.info("[크롤링 종료] {}, 가져온 도서수 : {}", library.getName(), books.size());
    }

//    public Response requestUrlAndGetResponse(String url) throws IOException {
//        final String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15";
//
//        return Jsoup.connect(url)
//                .userAgent(userAgent)
//                .referrer(library.getReferrer())
//                .ignoreContentType(true).execute();
//    }
//
//    public ResponseDto getResponseDto(Response response) throws JsonProcessingException, JAXBException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final Unmarshaller unmarshaller = JAXBContext.newInstance(XmlDto.class).createUnmarshaller();
//
//        if (response.contentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
//            return objectMapper.readValue(response.body(), JsonDto.class);
//        }
//        return (XmlDto) unmarshaller.unmarshal(new StringReader(response.body()));
//    }
//
//    public List<String> getDetailUrls(Integer totalBooks) {
//        // TODO : 테스트용으로 maxPage 설정해둠. 나중에 수정.
//        int maxPage = 2;
//        ArrayList<String> detailUrls = new ArrayList<>();
//        if (library.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
//            int size = 50;
////            int maxPage = (totalBooks / size) + 2;
//            for (int page = 1; page < maxPage; page++) {
//                detailUrls.add(library.getApiUrl() + "&pageIndex=" + page +"&recordCount=" + size);
//            }
//        }
//        if (library.getContentType().equals(MediaType.TEXT_XML_VALUE)) {
//            int size = 20;
////            int maxPage = (totalBooks / size) + 2;
//            for (int page = 1; page < maxPage; page++) {
//                detailUrls.add(library.getApiUrl() + "paging=" + page);
//            }
//        }
//        return detailUrls;
//    }
//
//    public List<BookDto> getBooks(List<String> detailUrls) {
//        List<BookDto> books = new ArrayList<>();
//        detailUrls.forEach(url -> {
//            try {
//                ResponseDto responseDto = getResponseDto(requestUrlAndGetResponse(url));
//                List<BookDto> bookDtos = responseDto.toBookDto();
//                books.addAll(bookDtos);
//            } catch (JsonProcessingException | JAXBException e) {
//                log.warn("*[DTO 파싱 실패] 도서관 = {}, url = {}", library.getName(), url);
//            } catch (IOException e) {
//                log.warn("*[예외 발생] 도서관 = {}, url = {}, {}", library.getName(), url, e.getLocalizedMessage());
//            }
//        });
//        return books;
//    }

}
