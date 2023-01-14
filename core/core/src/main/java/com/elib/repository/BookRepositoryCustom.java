package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.dto.BookListDto;
import com.elib.dto.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(Search search, Pageable pageable);

    boolean isEmpty();

    Book findByCore(Core core);

}
