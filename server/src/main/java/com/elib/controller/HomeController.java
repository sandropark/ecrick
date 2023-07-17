package com.elib.controller;

import com.elib.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final LibraryService libraryService;

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("libraryNames", libraryService.getNames());
        return "info";
    }

}