package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.dto.BookListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(String keyword, Pageable pageable);

    boolean isEmpty();

    Book findByCore(Core core);

}