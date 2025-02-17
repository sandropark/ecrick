package com.ecrick.crawler.web.controller;

import com.ecrick.crawler.domain.service.CrawlerService;
import com.ecrick.crawler.web.dto.LibraryAddRequestDto;
import com.ecrick.crawler.web.dto.LibraryEditFormDto;
import com.ecrick.crawler.web.dto.LibraryResponseDto;
import com.ecrick.crawler.web.dto.LibraryUpdateRequestDto;
import com.ecrick.domain.dto.LibraryDto;
import com.ecrick.domain.dto.Pagination;
import com.ecrick.domain.dto.Search;
import com.ecrick.domain.entity.ContentType;
import com.ecrick.domain.service.CoreService;
import com.ecrick.domain.service.LibraryService;
import com.ecrick.domain.service.PaginationService;
import com.ecrick.domain.service.VendorService;
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

import static com.ecrick.crawler.web.controller.LibraryController.ADMIN_LIBRARIES;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(ADMIN_LIBRARIES)
@Controller
public class LibraryController {

    public static final String ADMIN_LIBRARIES = "/admin/libraries";
    private final VendorService vendorService;
    private final PaginationService paginationService;
    private final LibraryService libraryService;
    private final CrawlerService crawlerService;
    private final CoreService coreService;

    @GetMapping
    public String libraries(
            @PageableDefault(size = 12, sort = "totalBooks") Pageable pageable,
            @ModelAttribute Search search, Model model) {
        // TODO : 검색 기능 구현
        // TODO : 다중 정렬 구현
        // TODO : 필터 구현
        Page<LibraryDto> libraries = libraryService.searchLibrary(pageable);
        Pagination pagination = paginationService.getDesktopPagination(libraries.getNumber(), libraries.getTotalPages());

        model.addAttribute("libraries", libraries);
        model.addAttribute("pagination", pagination);
        return "libraries/list";
    }

    @GetMapping("/form")
    public String libraryAddForm(Model model) {
        model.addAttribute("contentTypes", ContentType.values());
        model.addAttribute("vendorList", vendorService.findAll());
        return "libraries/addForm";
    }

    @PostMapping("/form")
    public String saveLibrary(@ModelAttribute LibraryAddRequestDto requestDto,
                              @RequestParam Long vendorId) {
        // TODO : 서비스에서 예외 발생시 redirect 하기
        libraryService.saveLibrary(requestDto.toDto(), vendorId);
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}")
    public String libraryDetail(@PathVariable Long libraryId, Model model) {
        model.addAttribute("library", LibraryResponseDto.from(libraryService.getLibrary(libraryId)));
        return "libraries/detail";
    }

    @GetMapping("/{libraryId}/form")
    public String libraryEditForm(@PathVariable Long libraryId, Model model) {
        model.addAttribute("form", LibraryEditFormDto.from(libraryService.getLibrary(libraryId)));
        model.addAttribute("contentTypes", ContentType.values());
        model.addAttribute("vendorList", vendorService.findAll());
        return "libraries/editForm";
    }

    @PostMapping("/{libraryId}/form")
    public String updateLibrary(
            @ModelAttribute LibraryUpdateRequestDto requestDto,
            @RequestParam Long vendorId,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        // TODO : 서비스에서 예외 발생시 redirect 하기
        libraryService.libraryUpdate(requestDto.toDto(), vendorId);
        redirectAttributes.addAttribute("libraryId", libraryId);
        return "redirect:" + ADMIN_LIBRARIES + "/{libraryId}";
    }

    @PostMapping("/{libraryId}/saved-update")
    public String updateSavedBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        libraryService.updateSavedBooks(libraryId);
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/delete")
    public String deleteLibrary(@PathVariable Long libraryId) {
        coreService.deleteByLibrary(libraryId);
        libraryService.delete(libraryId);
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/delete-books")
    public String deleteBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        coreService.deleteByLibrary(libraryId);
        libraryService.updateSavedBooks(libraryId);
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/crawl")
    public String crawl(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            @RequestParam(name = "thread-num", defaultValue = "1", required = false) int threadNum,
            @RequestParam(name = "sleep-time", defaultValue = "0", required = false) int sleepTime,
            RedirectAttributes redirectAttributes
    ) {
        crawlerService.init(libraryId, threadNum, sleepTime);
        new Thread(crawlerService).start();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/saved-update")
    public String savedBooksUpdate(
            @ModelAttribute QueryParam queryParam,
            RedirectAttributes redirectAttributes
    ) {
        libraryService.updateAllSavedBooks();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
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