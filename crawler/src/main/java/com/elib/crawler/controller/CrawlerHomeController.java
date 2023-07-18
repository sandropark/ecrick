package com.elib.crawler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.elib.crawler.controller.LibraryController.ADMIN_LIBRARIES;

@Controller
public class CrawlerHomeController {

    @GetMapping
    public String home() {
        return "redirect:" + ADMIN_LIBRARIES;
    }
}
