package sandro.elib.elib.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sandro.elib.elib.crawler.CrawlerService;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.dto.Pagination;
import sandro.elib.elib.repository.LibraryRepository;
import sandro.elib.elib.service.AdminService;
import sandro.elib.elib.service.LibraryService;
import sandro.elib.elib.service.PaginationService;
import sandro.elib.elib.web.dto.LibrarySearch;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final AdminService adminService;
    private final LibraryRepository libraryRepository;
    private final PaginationService paginationService;
    private final LibraryService libraryService;
    private final CrawlerService crawlerService;

    @GetMapping
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/library")
    public String library(
            @ModelAttribute LibrarySearch librarySearch,
            @PageableDefault(size = 20, sort = "totalBooks") Pageable pageable,
            Model model) {
        // TODO : 검색 기능 구현
        Page<Library> libraries = libraryRepository.findAll(pageable);
        Pagination pagination = paginationService.getPagination(libraries.getNumber(), libraries.getTotalPages());

        model.addAttribute("libraries", libraries);
        model.addAttribute("pagination", pagination);
        return "admin/library";
    }

    @PostMapping("/library")
    public String updateLibrary(
            @ModelAttribute QueryParam queryParam,
            RedirectAttributes redirectAttributes
    ) {
        libraryService.updateAllSavedBooks();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:/admin/library";
    }

    @PostMapping("/library/{libraryId}/delete")
    public String deleteLibrary(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        adminService.deleteLibrary(libraryId);  // TODO : 다른 객체 생각해보기
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:/admin/library";
    }

    @PostMapping("/library/{libraryId}/delete-books")
    public String deleteBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        adminService.deleteRelations(libraryId);  // TODO : 다른 객체 생각해보기
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:/admin/library";
    }

    @PostMapping("/library/{libraryId}/crawl")
    public String crawl(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        crawlerService.crawl(libraryId);
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:/admin/library";
    }

    @PostMapping("/library/{libraryId}/update")
    public String savedTotalBooksUpdate(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        libraryService.updateSavedBooks(libraryId);
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:/admin/library";
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