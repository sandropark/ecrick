package com.elib.controller;

import com.elib.dto.CoreDetailDto;
import com.elib.dto.CoreListDto;
import com.elib.dto.Pagination;
import com.elib.service.CoreService;
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

@RequiredArgsConstructor
@RequestMapping("/books")
@Controller
public class BookController {
    private final CoreService coreService;
    private final PaginationService paginationService;

    @GetMapping
    public String bookList(
            @PageableDefault(size = 24, sort = {"publicDate"}, direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute Search search, Model model
    ) {
        Page<CoreListDto> books = coreService.searchPage(search.getKeyword(), pageable);
        Pagination pagination = paginationService.getDesktopPagination(books.getNumber(), books.getTotalPages());
        model.addAttribute("books", books);
        model.addAttribute("pagination", pagination);
        return "desktop/list";
    }

    @GetMapping("/{bookId}")    // TODO : 도서관 / 서비스 보여줄 때 테이블로 보여주기
    public String bookDetail(@PathVariable Long bookId, Model model) {
        CoreDetailDto book = coreService.getBookDetail(bookId);
        model.addAttribute("book", book);
        return "book-detail";
    }

    @GetMapping("/mobile")
    public String mobileBookList(
            @PageableDefault(size = 20, sort = {"publicDate"}, direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute Search search, Model model
    ) {
        Page<CoreListDto> books = coreService.searchPage(search.getKeyword(), pageable);
        Pagination pagination = paginationService.getMobilePagination(books.getNumber(), books.getTotalPages());
        model.addAttribute("books", books);
        model.addAttribute("pagination", pagination);
        return "mobile/list";
    }
}
