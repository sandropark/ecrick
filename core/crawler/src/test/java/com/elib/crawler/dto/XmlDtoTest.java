package com.elib.crawler.dto;

import com.elib.dto.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class XmlDtoTest {

    @DisplayName("bookDto로 변환할 때 특수문자나 공백은 제거한다.")
    @Test
    void clean() throws Exception {
        // Given
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        XmlDto xmlDto = (XmlDto) unmarshaller.unmarshal(new File("src/test/resources/testdata.xml"));

        // When
        List<BookDto> bookDtos = xmlDto.toBookDto();

        // Then
        BookDto bookDto = bookDtos.get(0);
        assertThat(bookDto.getTitle()).isEqualTo("12가지 인생의 법칙");
        assertThat(bookDto.getAuthor()).isEqualTo("조던 B. 피터슨");
    }

}