package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BooksDto;
import sandro.elib.elib.dto.Page;
import sandro.elib.elib.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryService {

    private final BookRepository bookRepository;

    public List<BooksDto> searchBook(BookSearch bookSearch, Page page) {
        List<Book> books = bookRepository.findAll(bookSearch, page);
        return books.stream()
                .map(BooksDto::new)
                .collect(Collectors.toList());
    }

    public BookDto findById(Long bookId) {
        Book book = bookRepository.findById(bookId);
        return new BookDto(book);
    }
}
