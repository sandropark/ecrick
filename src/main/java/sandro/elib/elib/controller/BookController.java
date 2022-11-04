package sandro.elib.elib.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BookDetailDto;
import sandro.elib.elib.dto.BookListDto;
import sandro.elib.elib.dto.Pagination;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.service.BookService;
import sandro.elib.elib.service.PaginationService;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final PaginationService paginationService;

    @GetMapping
    public String bookList(
            @ModelAttribute BookSearch bookSearch,
            @PageableDefault(size = 24) Pageable pageable,
            Model model
    ) {
        Page<BookListDto> books = bookRepository.searchPage(bookSearch, pageable);
        Pagination pagination = paginationService.getPagination(books.getNumber(), books.getTotalPages());
        model.addAttribute("books", books);
        model.addAttribute("pagination", pagination);
        return "books/list";
    }

    @GetMapping("/{bookId}")
    public String bookDetail_SpringDataJpa(@PathVariable Long bookId, Model model) {
        BookDto book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "books/book-detail";
    }

}
