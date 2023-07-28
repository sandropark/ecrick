package com.ecrick.controller;

import com.ecrick.service.CrawlerCoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final CrawlerCoreService crawlerCoreService;

    @GetMapping
    public String index(Model model) {
        Map<String, Long> totalCoreAndBook = crawlerCoreService.getTotalCoreAndBook();
        model.addAttribute("totalCore", totalCoreAndBook.get("core"));
        model.addAttribute("totalBook", totalCoreAndBook.get("book"));
        return "index";
    }

    @PostMapping("/insert-books")
    public String create() {
        crawlerCoreService.insertBooksFromCoreAndSetBookId();
        return "redirect:/admin";
    }

    @PostMapping("/reduce-duplicate")
    public String reduceDuplicate() {
//        crawlerCoreService.reduceDuplicateFromDate();
        return "redirect:/admin";
    }

}
