//package com.ecrick.crawler;
//
//import com.ecrick.crawler.domain.webrequest.response.KyoboXmlResponse;
//import com.ecrick.dto.RowBookDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import java.util.List;
//
//class CrawlerUtilTest {
//
//    @DisplayName("실제로 크롤링에서 사용할 DTO 변환 테스트")
//    @Test
//    void xmlDtoTest() throws Exception {
//        JAXBContext jaxbContext = JAXBContext.newInstance(KyoboXmlResponse.class);
//
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//
//        // 실제로 사용할 때는 file 대신 response의 바디를 넣으면 된다. 바디는 문자열이기 때문에 StringReader를 사용해서 넣으면 된다.
//        KyoboXmlResponse kyoboXmlResponse = (KyoboXmlResponse) unmarshaller.unmarshal(new File("src/test/resources/kyobo.xml"));
//
//        List<RowBookDto> rowBooks = kyoboXmlResponse.toRowBooks(null);
//        for (RowBookDto rowBook : rowBooks) {
//            System.out.println("bookDto = " + rowBook);
//        }
//
//        Assertions.assertThat(rowBooks).hasSize(20);
//    }
//
//}
//
