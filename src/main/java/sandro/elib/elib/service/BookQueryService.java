package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryService {

    private final BookRepository bookRepository;

    public List<BookDto> getBookDtos() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }
}
