package com.ecrick.domain.service;

import com.ecrick.domain.dto.BookDetailDto;
import com.ecrick.domain.dto.BookListDto;
import com.ecrick.domain.dto.Search;
import com.ecrick.domain.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
