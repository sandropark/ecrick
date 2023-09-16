package com.ecrick.repository;

import com.ecrick.dto.BookListDto;
import com.ecrick.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(Search search, Pageable pageable);

}
