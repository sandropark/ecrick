package sandro.elib.elib.crawler;

import lombok.Getter;
import lombok.ToString;
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
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?",
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Kyobo_T3_Mobile/Tablet/Main/Ebook_List.asp?");
        Library jsonLibrary = Library.of("송파구통합도서관",
                "http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001",
                "http://ebook.splib.or.kr:8090/elibrary-front/content/contentList.ink?cttsDvsnCode=001");

        // When
        Response xmlResponse = requestUrlAndGetResponse(xmlLibrary);
        Response jsonResponse = requestUrlAndGetResponse(jsonLibrary);

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
    void getDetailUrlTest() throws Exception {
        // Given
        XmlDto xmlDto = mock(XmlDto.class);
        JsonDto jsonDto = mock(JsonDto.class);

        given(xmlDto.getTotalBooks()).willReturn(100);
        given(jsonDto.getTotalBooks()).willReturn(100);

        String xmlApiUrl = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?";
        String jsonApiUrl = "http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001";

        // When
        List<String> xmlDetailUrls = getDetailUrls(xmlDto, xmlApiUrl);
        List<String> jsonDetailUrls = getDetailUrls(jsonDto, jsonApiUrl);

        // Then
        System.out.println("xmlDetailUrls = " + xmlDetailUrls);
        System.out.println("jsonDetailUrls = " + jsonDetailUrls);
    }

    @Test
    void requestDetailUrl() throws Exception {
        // Given
        String detailUrl = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?paging=2";

        Library xmlLibrary = Library.of("구미시립도서관",
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?",
                "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Kyobo_T3_Mobile/Tablet/Main/Ebook_List.asp?");

        // When
        Response response = requestDetailUrlAndGetResponse(detailUrl, xmlLibrary);
        assertThat(response.statusCode()).isEqualTo(200);

        ResponseDto responseDto = responseToDto(response);
        List<BookDto> bookDtos = responseDto.toBookDto();

        // Then
        System.out.println("bookDtos.size() = " + bookDtos.size());
        for (BookDto bookDto : bookDtos) {
            System.out.println("bookDto = " + bookDto);
        }
    }

}

