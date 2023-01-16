package com.elib.crawler;

import com.elib.crawler.responsedto.ResponseDto;
import com.elib.crawler.responsedto.KyoboXmlDto;
import com.elib.domain.ContentType;
import com.elib.domain.Library;
import com.elib.domain.Vendor;
import com.elib.domain.VendorName;
import com.elib.dto.CoreDto;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import static com.elib.crawler.CrawlerUtil.getParser;
import static com.elib.crawler.CrawlerUtil.requestUrl;
import static org.jsoup.Connection.Response;

class CrawlerUtilTest {

    @DisplayName("실제로 크롤링에서 사용할 DTO 변환 테스트")
    @Test
    void xmlDtoTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(KyoboXmlDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // 실제로 사용할 때는 file 대신 response의 바디를 넣으면 된다. 바디는 문자열이기 때문에 StringReader를 사용해서 넣으면 된다.
        KyoboXmlDto kyoboXmlDto = (KyoboXmlDto) unmarshaller.unmarshal(new File("src/test/resources/testdata.xml"));

        List<CoreDto> coreDtos = kyoboXmlDto.toCoreDtos(null);
        for (CoreDto coreDto : coreDtos) {
            System.out.println("bookDto = " + coreDto);
        }

        Assertions.assertThat(coreDtos).hasSize(20);
    }

    @Disabled
    @Test
    void requestAndToDto() throws Exception {
        // Given
        LibraryCrawlerDto xmlLibraryDto = LibraryCrawlerDto.from(
                Library.builder()
                    .name("구미시립도서관")
                    .url("http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?")
                    .vendor(Vendor.builder().name(VendorName.KYOBO).build())
                    .contentType(ContentType.TEXT_XML)
                    .build()
        );

        LibraryCrawlerDto jsonLibraryDto = LibraryCrawlerDto.from(
                Library.builder()
                        .name("송파구통합도서관")
                        .url("http://ebook.splib.or.kr:8090/elibrary-front/content/contentListMobile.json?cttsDvsnCode=001")
                        .vendor(Vendor.builder().name(VendorName.KYOBO).build())
                        .contentType(ContentType.APPLICATION_JSON)
                        .build()
        );


        // When
        Response xmlResponse = requestUrl(xmlLibraryDto.getUrl());
        Response jsonResponse = requestUrl(jsonLibraryDto.getUrl());

        System.out.println("xmlResponse.contentType() = " + xmlResponse.contentType());
        System.out.println("xmlResponse.statusCode() = " + xmlResponse.statusCode());

        System.out.println("jsonResponse.contentType() = " + jsonResponse.contentType());
        System.out.println("jsonResponse.statusCode() = " + jsonResponse.statusCode());

        ResponseDto xmlResponseDto = getParser(xmlLibraryDto).parse(xmlResponse);
        ResponseDto jsonResponseDto = getParser(jsonLibraryDto).parse(jsonResponse);

        // Then
        System.out.println("xmlResponseDto.bookDto = " + xmlResponseDto.toCoreDtos(null));
        System.out.println("jsonResponseDto.bookDto() = " + jsonResponseDto.toCoreDtos(null));
    }

    @Test
    void requestDetailUrl() throws Exception {
        // Given
        String detailUrl = "http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?paging=2";

        LibraryCrawlerDto xmlLibraryDto = LibraryCrawlerDto.from(Library.builder()
                .name("구미시립도서관")
                .url("http://gumilib.yes24library.com:8085/kyobo_t3_mobile/Tablet/Main/Ebook_MoreView.asp?")
                .vendor(Vendor.builder().name(VendorName.KYOBO).build())
                .contentType(ContentType.TEXT_XML)
                .build()
        );

        // When
        Response response = requestUrl(detailUrl);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        ResponseDto responseDto = getParser(xmlLibraryDto).parse(response);
        List<CoreDto> coreDtos = responseDto.toCoreDtos(null);

        // Then
        System.out.println("bookDtos.size() = " + coreDtos.size());
        for (CoreDto coreDto : coreDtos) {
            System.out.println("bookDto = " + coreDto);
        }
    }

    @Disabled
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
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

}

