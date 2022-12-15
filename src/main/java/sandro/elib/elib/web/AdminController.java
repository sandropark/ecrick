package sandro.elib.elib.web;

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
import sandro.elib.elib.crawler.CrawlerService;
import sandro.elib.elib.crawler.UpdateCrawlerService;
import sandro.elib.elib.dto.LibraryDto;
import sandro.elib.elib.dto.Pagination;
import sandro.elib.elib.service.LibraryService;
import sandro.elib.elib.service.PaginationService;
import sandro.elib.elib.web.dto.LibraryAddFormDto;
import sandro.elib.elib.web.dto.LibrarySearch;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static sandro.elib.elib.web.AdminController.ADMIN_LIBRARIES;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(ADMIN_LIBRARIES)
@Controller
public class AdminController {

    public static final String ADMIN_LIBRARIES = "/admin/libraries";
    private final PaginationService paginationService;
    private final LibraryService libraryService;
    private final CrawlerService crawlerService;
    private final UpdateCrawlerService updateCrawlerService;

    @GetMapping
    public String libraries(
            @ModelAttribute LibrarySearch librarySearch,
            @PageableDefault(size = 12, sort = "totalBooks") Pageable pageable,
            Model model) {
        // TODO : 검색 기능 구현
        Page<LibraryDto> libraries = libraryService.searchLibrary(pageable);
        Pagination pagination = paginationService.getPagination(libraries.getNumber(), libraries.getTotalPages());

        model.addAttribute("libraries", libraries);
        model.addAttribute("pagination", pagination);
        return "admin/libraries/index";
    }

    @GetMapping("/form")
    public String libraryAddForm(Model model) {
        model.addAttribute("library", LibraryAddFormDto.of());
        return "admin/libraries/add-form";
    }

    @PostMapping("/form")
    public String saveLibrary(@ModelAttribute LibraryAddFormDto dto, HttpServletRequest request) {
        libraryService.save(dto);
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}")
    public String libraryDetail(@PathVariable Long libraryId, Model model) {
        LibraryDto library = libraryService.getLibraryDto(libraryId);
        model.addAttribute("library", library);
        return "admin/libraries/detail";
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

    @PostMapping("/{libraryId}/total-update")
    public String updateTotalBooks(
            @ModelAttribute QueryParam queryParam,
            @PathVariable Long libraryId,
            RedirectAttributes redirectAttributes
    ) {
        updateCrawlerService.init(libraryId);
        new Thread(updateCrawlerService).start();
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
            @RequestParam(name = "single-thread", defaultValue = "false", required = false) boolean singleThread,
            RedirectAttributes redirectAttributes
    ) {
        crawlerService.init(libraryId, singleThread);
        new Thread(crawlerService).start();
        redirectAttributes.addAllAttributes(queryParam.toMap());
        return "redirect:" + ADMIN_LIBRARIES;
    }

    @GetMapping("/{libraryId}/form")
    public String libraryEditForm(@PathVariable Long libraryId, Model model) {
        LibraryDto dto = libraryService.getLibraryDto(libraryId);
        model.addAttribute("library", dto);
        return "admin/libraries/edit-form";
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