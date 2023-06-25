package com.elib.repository;

import com.elib.dto.BookListDto;
import com.elib.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(Search search, Pageable pageable);

}
