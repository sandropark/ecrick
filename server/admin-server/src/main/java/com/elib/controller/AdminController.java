package com.elib.controller;

import com.elib.crawler.CrawlerService;
import com.elib.crawler.LibraryUpdateService;
import com.elib.dto.LibraryDto;
import com.elib.dto.Pagination;
import com.elib.service.LibraryService;
import com.elib.service.PaginationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

import static com.elib.controller.AdminController.ADMIN_LIBRARIES;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(ADMIN_LIBRARIES)
@Controller
public class AdminController {

    public static final String ADMIN_LIBRARIES = "/admin/libraries";
    private final PaginationService paginationService;
    private final LibraryService libraryService;
    private final CrawlerService crawlerService;
    private final LibraryUpdateService libraryUpdateService;

    @GetMapping
    public String libraries(
            @PageableDefault(size = 12, sort = "totalBooks") Pageable pageable,
            @ModelAttribute Search search, Model model) {
        // TODO : 검색 기능 구현
        Page<LibraryDto> libraries = libraryService.searchLibrary(pageable);
        Pagination pagination = paginationService.getDesktopPagination(libraries.getNumber(), libraries.getTotalPages());

        model.addAttribute("libraries", libraries);
        model.addAttribute("pagination", pagination);
        return "index";
    }

    @GetMapping("/form")
    public String libraryAddForm(Model model) {
        model.addAttribute("library", LibraryAddFormDto.of());
        return "add-form";
    }

    @PostMapping("/form")
    public String saveLibrary(@ModelAttribute LibraryAddFormDto dto) {
        libraryService.save(dto.toLibraryDto());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}")
    public String libraryDetail(@PathVariable Long libraryId, Model model) {
        LibraryDto library = libraryService.getLibraryDto(libraryId);
        model.addAttribute("library", library);
        return "detail";
    }

    @PostMapping("/{libraryId}/total-update")
    public String updateTotalBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        libraryUpdateService.init(libraryId, "total");
        new Thread(libraryUpdateService).start();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/saved-update")
    public String updateSavedBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        libraryUpdateService.init(libraryId, "saved");
        new Thread(libraryUpdateService).start();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/delete")
    public String deleteLibrary(@PathVariable Long libraryId) {
        libraryService.delete(libraryId);
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/crawl")
    public String crawl(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            @RequestParam(name = "thread-num", defaultValue = "1", required = false) int threadNum,
            @RequestParam(name = "sleep-time",defaultValue = "0",required = false) int sleepTime,
            RedirectAttributes redirectAttributes
    ) {
        crawlerService.init(libraryId, threadNum, sleepTime);
        new Thread(crawlerService).start();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}/form")
    public String libraryEditForm(@PathVariable Long libraryId, Model model) {
        LibraryDto dto = libraryService.getLibraryDto(libraryId);
        model.addAttribute("library", dto);
        return "edit-form";
    }

    @PostMapping("/{libraryId}/form")
    public String updateLibrary(
            @ModelAttribute LibraryDto dto,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        libraryService.update(dto);
        redirectAttributes.addAttribute("libraryId", libraryId);
        return "redirect:" + ADMIN_LIBRARIES + "/{libraryId}";
    }

    @Data
    static class QueryParam {
        private String keyword;
        private String page;
        private String sort;
        private String size;

        public Map<String, String> toMap() {
            return Map.of("keyword", keyword, "page", page, "sort", sort, "size", size);
        }

    }

}