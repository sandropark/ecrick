package com.elib.service;

import com.elib.dto.BookDetailDto;
import com.elib.dto.BookListDto;
import com.elib.dto.Search;
import com.elib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Page<BookListDto> searchPage(Search search, Pageable pageable) {
        return bookRepository.searchPage(search, pageable);
    }

    public BookDetailDto getBookDetail(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookDetailDto::from)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다. id = " + bookId));
    }

}
