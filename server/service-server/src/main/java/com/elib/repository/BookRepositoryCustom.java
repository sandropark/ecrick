package com.elib.repository;

import com.elib.domain.Book;
import com.elib.dto.BookDto;
import com.elib.dto.BookListDto;
import com.elib.web.dto.BookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(BookSearch bookSearch, Pageable pageable);

    Book findByDto(BookDto bookDto);
}
