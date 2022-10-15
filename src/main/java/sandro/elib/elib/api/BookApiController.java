package sandro.elib.elib.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sandro.elib.elib.dto.BookDto;
import sandro.elib.elib.service.BookQueryService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookApiController {
    private final BookQueryService bookQueryService;

    @GetMapping
    public List<BookDto> books() {
        return bookQueryService.getBookDtos();
    }

}
