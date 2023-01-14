package com.elib.controller;

import com.elib.dto.BookDetailDto;
import com.elib.dto.BookListDto;
import com.elib.dto.Pagination;
import com.elib.dto.Search;
import com.elib.service.BookService;
import com.elib.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {
    private final BookService bookService;
    private final PaginationService paginationService;

    @GetMapping
    public String bookList(
            @PageableDefault(size = 24, sort = {"publicDate"}, direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute Search search,
            Model model,
            HttpServletRequest request
    ) {
        Page<BookListDto> books = bookService.searchPage(search, pageable);
        Pagination pagination = paginationService.getDesktopPagination(books.getNumber(), books.getTotalPages());
        model.addAttribute("books", books);
        model.addAttribute("pagination", pagination);

        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Mobile")) {
            return "mobile/list";
        }
        return "desktop/list";
    }

    @GetMapping("/{bookId}")    // TODO : 도서관 / 서비스 보여줄 때 테이블로 보여주기
    public String bookDetail(
            @PathVariable Long bookId,
            @ModelAttribute Search search,
            Model model) {
        BookDetailDto book = bookService.getBookDetail(bookId);
        model.addAttribute("book", book);
        return "book-detail";
    }
}
