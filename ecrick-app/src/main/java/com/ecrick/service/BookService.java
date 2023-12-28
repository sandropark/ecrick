package com.ecrick.service;

import com.ecrick.dto.BookDetailDto;
import com.ecrick.dto.BookListDto;
import com.ecrick.dto.Search;
import com.ecrick.repository.JpaBookRepository;
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

    private final JpaBookRepository jpaBookRepository;

    public Page<BookListDto> searchPage(Search search, Pageable pageable) {
        return jpaBookRepository.searchPage(search, pageable);
    }

    public BookDetailDto getBookDetail(Long bookId) {
        return jpaBookRepository.findById(bookId)
                .map(BookDetailDto::from)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다. id = " + bookId));
    }

}
