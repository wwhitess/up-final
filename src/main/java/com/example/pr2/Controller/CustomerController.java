package com.example.pr2.Controller;

import com.example.pr2.Model.Customer;
import com.example.pr2.Repositories.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", customerRepository.findAll());
        model.addAttribute("modelName", "customers");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Customer());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "customers");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", customerRepository.findById(id).orElseThrow());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "customers");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customers/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", customerRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "customers");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers/view";
    }
}
