package sandro.elib.elib.dto;

import lombok.Data;

@Data
public class MyPage {
    private int page = 1;
    private int size = 24;
    private int nowPage;
    private int startPage;
    private int endPage;
    private int nextStartPage;
    private int preStartPage;

    public void setUp() {
        this.nowPage = page;
        this.startPage = getStartPage(nowPage);
        this.endPage = startPage + 9;
        this.nextStartPage = getNextStartPage(nowPage);
        this.preStartPage = getPreStartPage(nowPage);
    }

    public int getFirstResult() {
        return (page - 1) * size;
    }

    public int getMaxResult() {
        return size;
    }

    public void setDefault() {
        page = 0;
        size = 20;
    }

    private int getStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return nowPage / 10 * 10 + 1;
    }

    private int getNextStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return nowPage / 10 * 10 + 11;
    }

    private int getPreStartPage(int nowPage) {
        if (nowPage % 10 == 0) {
            nowPage -= 1;
        }
        return Math.max(nowPage / 10 * 10 - 10 + 1, 1);
    }
}
