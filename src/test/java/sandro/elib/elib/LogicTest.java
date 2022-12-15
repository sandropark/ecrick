package sandro.elib.elib;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Random;

import static java.lang.Math.abs;
import static org.assertj.core.api.Assertions.assertThat;

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

}
