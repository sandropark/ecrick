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
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BooksDto;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.service.BookService;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping
    public String list_SpringDataJpa(@ModelAttribute BookSearch bookSearch,
                                     @PageableDefault(size = 24) Pageable pageable,
                                     Model model) {
        Page<BooksDto> page = bookRepository.searchPage(bookSearch, pageable);
        int startPage = page.getNumber() / 10 * 10 + 1;
        int endPage = Math.min(page.getTotalPages(), page.getNumber() / 10 * 10 + 10);
        int preStartPage = Math.max(page.getNumber() / 10 * 10 - 10 + 1, 1);
        int nextStartPage = page.getNumber() / 10 * 10 + 11;

        model.addAttribute("page", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("preStartPage", preStartPage);
        model.addAttribute("nextStartPage", nextStartPage);

        model.addAttribute("books", page.getContent());
        return "books/list";
    }

    @GetMapping("/{bookId}")
    public String bookDetail_SpringDataJpa(@PathVariable Long bookId, Model model) {
        BookDto book = bookService.findById(bookId);
        model.addAttribute("book", book);
        return "books/book-detail";
    }

}
