package com.elib.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController {
    @GetMapping
    public String index(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("Mobile")) {
            return "redirect:/books/mobile";
        }
        return "redirect:/books";
    }
}
