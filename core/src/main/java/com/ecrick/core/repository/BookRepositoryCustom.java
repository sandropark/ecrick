package com.ecrick.core.repository;

import com.ecrick.core.dto.BookListDto;
import com.ecrick.core.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(Search search, Pageable pageable);

}
