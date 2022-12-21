package com.elib.service;

import com.elib.domain.Book;
import com.elib.dto.BookDetailDto;
import com.elib.dto.BookDto;
import com.elib.dto.BookListDto;
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

    public BookDetailDto getBookDetail(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookDetailDto::from)
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다 - bookId: " + bookId));
    }

    public Page<BookListDto> searchPage(String keyword, Pageable pageable) {
        return bookRepository.searchPage(keyword, pageable);
    }

    @Transactional
    public Book saveBookDto(BookDto bookDto) {
        Book book = this.bookRepository.findByDto(bookDto);
        if (book == null) {
            book = bookDto.toEntity();
            this.bookRepository.save(book);
        } else if (book.hasNotPublicDate() && bookDto.hasPublicDate()) {
            book.updatePublicDate(bookDto.getPublicDate());
        }
        return book;
    }

}