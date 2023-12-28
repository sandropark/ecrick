package com.ecrick.controller;

import com.ecrick.common.ContentType;
import com.ecrick.controller.request.LibraryAddRequest;
import com.ecrick.controller.request.LibraryUpdateRequest;
import com.ecrick.controller.response.LibraryEditForm;
import com.ecrick.controller.response.LibraryResponse;
import com.ecrick.crawler.service.CrawlerService;
import com.ecrick.crawler.service.LibraryService;
import com.ecrick.crawler.service.RowBookService;
import com.ecrick.dto.Pagination;
import com.ecrick.dto.Search;
import com.ecrick.entity.Library;
import com.ecrick.repository.VendorRepository;
import com.ecrick.service.PaginationService;
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

import static com.ecrick.controller.LibraryController.ADMIN_LIBRARIES;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(ADMIN_LIBRARIES)
@Controller
public class LibraryController {

    public static final String ADMIN_LIBRARIES = "/admin/libraries";
    private final VendorRepository vendorRepository;
    private final PaginationService paginationService;
    private final LibraryService libraryService;
    private final CrawlerService crawlerService;
    private final RowBookService rowBookService;

    @GetMapping
    public String libraries(
            @PageableDefault(size = 12, sort = "totalBooks") Pageable pageable,
            @ModelAttribute Search search, Model model) {
        // TODO : 검색 기능 구현
        // TODO : 다중 정렬 구현
        // TODO : 필터 구현
        Page<Library> libraries = libraryService.searchLibrary(pageable);
        Pagination pagination = paginationService.getDesktopPagination(libraries.getNumber(), libraries.getTotalPages());

        model.addAttribute("libraries", libraries);
        model.addAttribute("pagination", pagination);
        return "libraries/list";
    }

    @GetMapping("/form")
    public String libraryAddForm(Model model) {
        model.addAttribute("contentTypes", ContentType.values());
        model.addAttribute("vendorList", vendorRepository.findAll());
        return "libraries/addForm";
    }

    @PostMapping("/form")
    public String saveLibrary(@ModelAttribute LibraryAddRequest requestDto,
                              @RequestParam Long vendorId) {
        // TODO : 서비스에서 예외 발생시 redirect 하기
        libraryService.saveLibrary(requestDto.toModel(vendorId));
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}")
    public String libraryDetail(@PathVariable Long libraryId, Model model) {
        model.addAttribute("library", LibraryResponse.from(libraryService.getLibrary(libraryId)));
        return "libraries/detail";
    }

    @GetMapping("/{libraryId}/form")
    public String libraryEditForm(@PathVariable Long libraryId, Model model) {
        model.addAttribute("form", LibraryEditForm.from(libraryService.getLibrary(libraryId)));
        model.addAttribute("contentTypes", ContentType.values());
        model.addAttribute("vendorList", vendorRepository.findAll());
        return "libraries/editForm";
    }

    @PostMapping("/{libraryId}/form")
    public String updateLibrary(
            @ModelAttribute LibraryUpdateRequest requestDto,
            @RequestParam Long vendorId,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        // TODO : 서비스에서 예외 발생시 redirect 하기
        libraryService.libraryUpdate(requestDto.toModel(), vendorId);
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
        rowBookService.deleteByLibrary(libraryId);
        libraryService.delete(libraryId);
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @PostMapping("/{libraryId}/delete-books")
    public String deleteBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        rowBookService.deleteByLibrary(libraryId);
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
        // TODO : 크롤링
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
            return Map.of("keyword", keyword, "page", page, "sort", sort, "requestSize", size);
        }

    }

}