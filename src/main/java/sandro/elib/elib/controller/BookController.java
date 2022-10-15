package sandro.elib.elib.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.service.BookQueryService;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookQueryService bookQueryService;

    @GetMapping
    public String list(Model model) {
        List<BookDto> bookDtos = bookQueryService.getBookDtos();
        model.addAttribute("books", bookDtos);
        return "books/list";
    }


}
