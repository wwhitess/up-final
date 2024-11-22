package com.example.pr2.Controller;

import com.example.pr2.Model.Manufacturer;
import com.example.pr2.Repositories.ManufacturerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerController(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", manufacturerRepository.findAll());
        model.addAttribute("modelName", "manufacturers");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Manufacturer());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "manufacturers");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturers/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", manufacturerRepository.findById(id).orElseThrow());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "manufacturers");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturers/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", manufacturerRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "manufacturers");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        manufacturerRepository.deleteById(id);
        return "redirect:/manufacturers/view";
    }
}
