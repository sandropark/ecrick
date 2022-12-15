package sandro.elib.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Slf4j
@RequestMapping("/")
@Controller
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request) {
        Iterator<String> iterator = request.getHeaderNames().asIterator();
        while (iterator.hasNext()) {
            String headerName = iterator.next();
            log.info("{} = {}", headerName, request.getHeader(headerName));
        }
        return "redirect:/books";
    }

}