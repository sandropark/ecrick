package com.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.elib.domain.Book;
import com.elib.dto.BookDto;
import com.elib.dto.BookListDto;
import com.elib.web.dto.BookSearch;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(BookSearch bookSearch, Pageable pageable);

    Book findByDto(BookDto bookDto);
}
