package sandro.elib.elib.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PagingTest {

    @DisplayName("현재 페이지에 따라서 10개의 페이지 번호 시작 숫자 찾기")
    @ParameterizedTest
    @CsvSource({"1, 1", "5, 1", "10, 1", "11, 11", "13, 11", "20, 11", "21, 21"})
    void startPage(int nowPage, int startPage) throws Exception {
        assertThat(getStartPage(nowPage)).isEqualTo(startPage);
    }

    private int getStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return nowPage / 10 * 10 + 1;
    }

    @DisplayName("다음 페이지를 클릭했을 때 현재 페이지에 따라서 다음 10개의 페이지 시작 번호 찾기")
    @ParameterizedTest
    @CsvSource({"1, 11", "5, 11", "10, 11", "11, 21", "13, 21", "20, 21", "21, 31"})
    void nextPage(int nowPage, int nextStartPage) throws Exception {
        assertThat(getNextStartPage(nowPage)).isEqualTo(nextStartPage);
    }

    private int getNextStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return nowPage / 10 * 10 + 11;
    }

    @DisplayName("이전 페이지를 클릭했을 때 현재 페이지에 따라서 이전 10개의 페이지 시작 번호 찾기")
    @ParameterizedTest
    @CsvSource({"1, 1", "5, 1", "10, 1", "11, 1", "13, 1", "20, 1", "21, 11", "31, 21", "40, 21"})
    void previousPage(int nowPage, int previousStartPage) throws Exception {
        assertThat(getPreviousStartPage(nowPage)).isEqualTo(previousStartPage);
    }

    private int getPreviousStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return Math.max(nowPage / 10 * 10 - 10 + 1, 1);
    }
}
