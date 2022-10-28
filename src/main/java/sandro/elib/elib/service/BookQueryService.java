package sandro.elib.elib.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BooksDto;
import sandro.elib.elib.dto.MyPage;
import sandro.elib.elib.repository.BookJPARepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookQueryService {

    private final BookJPARepository bookJPARepository;

    public List<BooksDto> searchBook(BookSearch bookSearch, MyPage myPage) {
        List<Book> books = bookJPARepository.findAll(bookSearch, myPage);
        return books.stream()
                .map(BooksDto::new)
                .collect(Collectors.toList());
    }

    public BookDto findById(Long bookId) {
        Book book = bookJPARepository.findById(bookId);
        return new BookDto(book);
    }
}
