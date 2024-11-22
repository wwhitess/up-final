package com.example.pr2.Controller;

import com.example.pr2.Model.User;
import com.example.pr2.Repositories.RoleRepository;
import com.example.pr2.Repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("modelName", "users");
        return "view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new User());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "users");
        return "add_update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") User users) {
        userRepository.save(users);
        return "redirect:/users/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "users");
        return "add_update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") User users) {
        userRepository.save(users);
        return "redirect:/users/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", userRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "users");
        return "delete";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users/view";
    }
}
