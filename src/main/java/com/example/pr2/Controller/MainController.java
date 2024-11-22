package com.example.pr2.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        String role = authentication.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
