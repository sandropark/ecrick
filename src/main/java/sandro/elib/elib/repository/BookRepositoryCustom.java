package sandro.elib.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BookDto;

public interface BookRepositoryCustom {
    Page<BookDto> searchPage(BookSearch bookSearch, Pageable pageable);
}
