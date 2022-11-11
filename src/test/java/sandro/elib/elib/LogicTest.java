package sandro.elib.elib;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class LogicTest {

    @Test
    void localDateTest() throws Exception {
        String date = "2022-11-30";
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

}
