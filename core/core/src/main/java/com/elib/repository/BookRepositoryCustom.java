package com.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.elib.domain.Book;
import com.elib.dto.BookDto;
import com.elib.dto.BookListDto;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(String keyword, Pageable pageable);

    Book findByDto(BookDto bookDto);
}
