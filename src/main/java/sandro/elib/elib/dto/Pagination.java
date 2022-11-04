package sandro.elib.elib.dto;

import lombok.Data;

@Data
public class Pagination {

    private int startPage;
    private int endPage;
    private int preStartPage;
    private int nextStartPage;

}
