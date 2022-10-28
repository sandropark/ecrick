package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto findById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(BookDto::new).orElse(null);
    }

}
