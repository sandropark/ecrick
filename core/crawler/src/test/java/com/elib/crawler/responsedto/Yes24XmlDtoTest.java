package com.elib.crawler.responsedto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Yes24XmlDtoTest {

    @DisplayName("다양한 형식의 문자열을 받아서 LocalDate로 변환한다.")
    @Test
    void getLocalDateTest() throws Exception {
        // Given
        List<String> inputs = List.of("20230101", "2023-01-01");

        // When
        List<LocalDate> results = inputs.stream()
                .map(this::getLocalDate)
                .collect(Collectors.toList());

        // Then
        assertThat(results.get(0)).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(results.get(1)).isEqualTo(LocalDate.of(2023, 1, 1));
    }

    private LocalDate getLocalDate(String input) {
        if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(input);
        }
        if (input.matches("\\d{8}")) {
            return LocalDate.parse(input, DateTimeFormatter.BASIC_ISO_DATE);
        }
        return null;
    }

}