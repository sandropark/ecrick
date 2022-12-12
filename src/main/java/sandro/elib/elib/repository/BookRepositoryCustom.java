package sandro.elib.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BookListDto;
import sandro.elib.elib.web.dto.BookSearch;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(BookSearch bookSearch, Pageable pageable);

    Book findByDto(BookDto bookDto);
}
