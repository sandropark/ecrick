package sandro.elib.elib.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {

    @GetMapping
    public String home() {
        return "redirect:/books";
    }

}