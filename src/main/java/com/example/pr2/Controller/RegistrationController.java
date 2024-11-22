package com.example.pr2.Controller;

import com.example.pr2.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String username, @RequestParam String password,
                               @RequestParam(defaultValue = "ROLE_USER") String roleName, Model model) {
        if (userService.isUsernameTaken(username)) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }
        userService.registerNewUser(username, password, roleName);
        return "redirect:/login";
    }

}
