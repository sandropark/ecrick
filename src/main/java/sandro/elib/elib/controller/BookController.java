package sandro.elib.elib.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sandro.elib.elib.domain.BookSearch;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.dto.BooksDto;
import sandro.elib.elib.service.BookQueryService;
import sandro.elib.elib.dto.Page;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookQueryService bookQueryService;

    @GetMapping
    public String list(@ModelAttribute BookSearch bookSearch,
                       @ModelAttribute Page page, Model model) {
        List<BooksDto> booksDtos = bookQueryService.searchBook(bookSearch, page);
        page.setUp();
        model.addAttribute("books", booksDtos);
        return "books/list";
    }

    @GetMapping("/{bookId}")
    public String bookDetail(@PathVariable Long bookId, Model model) {
        BookDto book = bookQueryService.findById(bookId);
        model.addAttribute("book", book);
        return "books/book-detail";
    }


}
