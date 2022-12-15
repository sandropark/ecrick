package sandro.elib.elib.web;

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
import sandro.elib.elib.dto.BookDetailDto;
import sandro.elib.elib.dto.BookListDto;
import sandro.elib.elib.dto.Pagination;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.service.BookService;
import sandro.elib.elib.service.PaginationService;
import sandro.elib.elib.web.dto.BookSearch;

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

    @GetMapping("/{bookId}")    // TODO : 도서관 / 서비스 보여줄 때 테이블로 보여주기
    public String bookDetail(@PathVariable Long bookId, Model model) {
        BookDetailDto book = bookService.getBookDetail(bookId);
        model.addAttribute("book", book);
        return "books/book-detail";
    }

}
