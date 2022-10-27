package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.repository.BookJPARepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookJPARepository bookJPARepository;

    public BookDto findById(Long bookId) {
        Optional<Book> book = bookJPARepository.findById(bookId);
        return book.map(BookDto::new).orElse(null);
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookJPARepository.findAll(pageable);
    }
}
