package com.ecrick.controller;

import com.ecrick.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final LibraryRepository libraryRepository;

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("libraryNames", getAllLibraryNames());
        return "info";
    }

    private List<String> getAllLibraryNames() {
        Pattern compile = Pattern.compile("\\(.+\\)");
        return libraryRepository.findAllNames().stream()
                .map(name -> compile.matcher(name).replaceAll(""))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

}