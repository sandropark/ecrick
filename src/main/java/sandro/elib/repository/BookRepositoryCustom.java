package sandro.elib.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sandro.elib.domain.Book;
import sandro.elib.dto.BookDto;
import sandro.elib.dto.BookListDto;
import sandro.elib.web.dto.BookSearch;

public interface BookRepositoryCustom {
    Page<BookListDto> searchPage(BookSearch bookSearch, Pageable pageable);

    Book findByDto(BookDto bookDto);
}
