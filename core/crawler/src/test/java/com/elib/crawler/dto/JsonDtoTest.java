package com.elib.crawler.dto;

import com.elib.dto.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDtoTest {

    @DisplayName("bookDto로 변환할 때 특수문자나 공백은 제거한다.")
    @Test
    void clean() throws Exception {
        // Given
        List<Map<String, String>> mapList = List.of(new HashMap<>() {{
            put("cttsHnglName", "사피엔스 ");
            put("sntnAuthName", " 하라&asf;리 저");
            put("pbcmName", "김영&gt;사");
            put("publDate", "");
            put("coverImage", "");
        }});
        JsonDto jsonDto = new JsonDto();
        jsonDto.setContentList(mapList);

        // When
        List<BookDto> bookDtos = jsonDto.toBookDto();

        // Then
        BookDto bookDto = bookDtos.get(0);
        assertThat(bookDto.getTitle()).isEqualTo("사피엔스");
        assertThat(bookDto.getAuthor()).isEqualTo("하라리");
        assertThat(bookDto.getPublisher()).isEqualTo("김영사");
    }

}