package com.elib;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
class LogicTest {

    @Test
    void localDateTest() throws Exception {
        String date = "2022-11-30";
        LocalDate parse = LocalDate.parse(date);
        System.out.println("parse = " + parse);
    }

    @Test
    void subString() throws Exception {
        String target = "hello/";
        String actual = target.substring(0, target.length()-1);
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    void parseUrl() throws Exception {
        String input = "src=\"https://ebook.gblib.or.kr/DRMContent/ebook/4801170432389/M4801170432389.jpg\" alt=\"나는 당신이 N잡러가 되었으면 좋겠습니다\"";
        String[] split = input.split("\"");
        for (String s : split) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void random() throws Exception {
        Random random = new Random();
        for (int j = 0; j < 10; j++) {
            int i = abs(random.nextInt(30));
            System.out.println("i = " + i);
        }
        Thread.sleep(3000);
        System.out.println("!!");
    }

    @Disabled
    @DisplayName("정규식 테스트")
    @Test
    void regex() throws Exception {
        // Given
        String input = "인생이 바뀌는 독서법 알려드립니다";

        // When
        Pattern pattern = Pattern.compile("독서");
        Matcher matcher = pattern.matcher(input);

        // Then
        assertThat(matcher.find()).isTrue();
        System.out.println("group = " + matcher.group());
    }
    @Disabled
    @Test
    void regex2() throws Exception {
        // Given
        String input = "J.K rolling,<harari>,기안84";

        // When
        String result = input.replaceAll("[^가-힣a-zA-Z0-9., ]", "");

        // Then
        assertThat(result).isEqualTo("J.K rolling,harari,기안84");
    }

    @DisplayName("LocalDate.parse의 기본 파싱 형식은 yyyy-MM-dd 다. 다른 형식을 넣을 경우 예외가 발생한다.")
    @Test
    void localDate() throws Exception {
        String input = "20230101";
        assertThatThrownBy(() -> LocalDate.parse(input))
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void localDate2() throws Exception {
        String input = "20230101";

        LocalDate localDate = LocalDate.parse(input, DateTimeFormatter.BASIC_ISO_DATE);

        System.out.println("localDate = " + localDate);
    }


}
