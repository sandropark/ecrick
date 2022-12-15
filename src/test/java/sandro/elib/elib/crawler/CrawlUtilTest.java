package sandro.elib.elib.crawler;

import lombok.Getter;
import lombok.ToString;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sandro.elib.elib.crawler.dto.JsonDto;
import sandro.elib.elib.crawler.dto.ResponseDto;
import sandro.elib.elib.crawler.dto.XmlDto;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.dto.BookDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsoup.Connection.Response;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static sandro.elib.elib.crawler.CrawlUtil.*;

class CrawlUtilTest {

    @DisplayName("테스트 xml 파일을 객체로 파싱하는 테스트")
    @Test
    void parseXml() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        XMLDto xmlDto = (XMLDto) unmarshaller.unmarshal(new File("src/test/resources/testdata.xml"));
        System.out.println("xmlDto.totalCount = " + xmlDto.getTotalCount());
        System.out.println("xmlDto.contents.size = " + xmlDto.getContents().size());
        List<XMLDto.Item> contents = xmlDto.getContents();
        for (XMLDto.Item content : contents) {
            System.out.println("content = " + content.getContents());
        }

        assertThat(contents.get(0).getContents().getTitle()).isEqualTo("12가지 인생의 법칙");
    }

    @Getter
    @XmlRootElement(name = "channel")
    static class XMLDto {
        @XmlElement(name = "listCount")
        private Integer totalCount;
        @XmlElement(name = "list")
        private List<Item> contents;

        @Getter
        static class Item {
            @XmlElement(name = "item")
            private Contents contents;

            @ToString
            @Getter
            static class Contents {
                @XmlElement(name = "image")
                private String imageUrl;
                @XmlElement(name = "product_nm_kr")
                private String title;
                @XmlElement(name = "text_author_nm")
                private String author;
                @XmlElement(name = "cp_nm1")
                private String publisher;
            }
        }
    }

    @DisplayName("실제로 크롤링에서 사용할 DTO 변환 테스트")
    @Test
    void xmlDtoTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // 실제로 사용할 때는 file 대신 response의 바디를 넣으면 된다. 바디는 문자열이기 때문에 StringReader를 사용해서 넣으면 된다.
        XmlDto xmlDto = (XmlDto) unmarshaller.unmarshal(new File("src/test/resources/testdata.xml"));

        List<BookDto> bookDtos = xmlDto.toBookDto();
        for (BookDto bookDto : bookDtos) {
            System.out.println("bookDto = " + bookDto);
        }

        assertThat(bookDtos).hasSize(20);
    }

    @Test
    void requestAndToDto() throws Exception {
        // Given
        Library xmlLibrary = Library.of("구미시립도서관",
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?");
        Library jsonLibrary = Library.of("송파구통합도서관",
                "http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001");

        // When
        Response xmlResponse = requestUrl(xmlLibrary.getUrl());
        Response jsonResponse = requestUrl(jsonLibrary.getUrl());

        System.out.println("xmlResponse.contentType() = " + xmlResponse.contentType());
        System.out.println("xmlResponse.statusCode() = " + xmlResponse.statusCode());

        System.out.println("jsonResponse.contentType() = " + jsonResponse.contentType());
        System.out.println("jsonResponse.statusCode() = " + jsonResponse.statusCode());

        ResponseDto xmlResponseDto = responseToDto(xmlResponse);
        ResponseDto jsonResponseDto = responseToDto(jsonResponse);

        // Then
        System.out.println("xmlResponseDto.bookDto = " + xmlResponseDto.toBookDto());
        System.out.println("jsonResponseDto.bookDto() = " + jsonResponseDto.toBookDto());
    }

    @Test
    void requestDetailUrl() throws Exception {
        // Given
        String detailUrl = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?paging=2";

        Library xmlLibrary = Library.of("구미시립도서관",
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?");

        // When
        Response response = requestUrl(detailUrl);
        assertThat(response.statusCode()).isEqualTo(200);

        ResponseDto responseDto = responseToDto(response);
        List<BookDto> bookDtos = responseDto.toBookDto();

        // Then
        System.out.println("bookDtos.size() = " + bookDtos.size());
        for (BookDto bookDto : bookDtos) {
            System.out.println("bookDto = " + bookDto);
        }
    }

    @DisplayName("Jsoup으로 요청하는 헤더를 브라우저로 요청하는 헤더와 같게 만든다.")
    @Test
    void request() throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "ko,en-US;q=0.9,en;q=0.8,ko-KR;q=0.7");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.5249.207 Whale/3.17.145.18 Safari/537.36");

        Response response = Jsoup.connect("http://localhost:8080")
                .headers(headers)
                .execute();
        assertThat(response.statusCode()).isEqualTo(200);
    }

}

