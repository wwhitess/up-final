package com.example.pr2.Controller;

import com.example.pr2.Model.Role;
import com.example.pr2.Repositories.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", roleRepository.findAll());
        model.addAttribute("modelName", "roles");
        return "view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Role());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "roles");
        return "add_update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Role role) {
        roleRepository.save(role);
        return "redirect:/roles/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", roleRepository.findById(id).orElseThrow());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "roles");
        return "add_update";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Role role) {
        roleRepository.save(role);
        return "redirect:/roles/view";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", roleRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "roles");
        return "delete";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return "redirect:/roles/view";
    }
}
