package sandro.elib.elib.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Pagination {

    private List<Integer> pageNumbers = new ArrayList<>();
    private int preStartPage;
    private int nextStartPage;

}
