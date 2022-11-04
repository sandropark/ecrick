package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.dto.BookDetailDto;
import sandro.elib.elib.repository.BookRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDetailDto findById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(BookDetailDto::new)
                .orElseThrow(() -> new EntityNotFoundException("도서 정보가 없습니다 - bookId: " + bookId));
    }

}
