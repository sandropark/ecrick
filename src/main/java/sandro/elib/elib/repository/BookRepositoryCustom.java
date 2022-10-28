package sandro.elib.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BooksDto;

public interface BookRepositoryCustom {
    Page<BooksDto> searchPage(BookSearch bookSearch, Pageable pageable);
}