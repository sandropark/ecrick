package com.ecrick.crawler;

import com.ecrick.core.dto.CoreDto;
import com.ecrick.crawler.dto.KyoboXmlDto;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class CrawlerUtilTest {

    @DisplayName("실제로 크롤링에서 사용할 DTO 변환 테스트")
    @Test
    void xmlDtoTest() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(KyoboXmlDto.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // 실제로 사용할 때는 file 대신 response의 바디를 넣으면 된다. 바디는 문자열이기 때문에 StringReader를 사용해서 넣으면 된다.
        KyoboXmlDto kyoboXmlDto = (KyoboXmlDto) unmarshaller.unmarshal(new File("src/test/resources/kyobo.xml"));

        List<CoreDto> coreDtos = kyoboXmlDto.toCoreDtos(null);
        for (CoreDto coreDto : coreDtos) {
            System.out.println("bookDto = " + coreDto);
        }

        Assertions.assertThat(coreDtos).hasSize(20);
    }

}

