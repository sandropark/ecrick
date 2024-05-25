package com.ecrick.domain.repository;

import com.ecrick.domain.dto.BookListDto;
import com.ecrick.domain.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(Search search, Pageable pageable);

}
