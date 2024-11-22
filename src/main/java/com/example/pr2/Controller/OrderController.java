package com.example.pr2.Controller;

import com.example.pr2.Model.Order;
import com.example.pr2.Repositories.CustomerRepository;
import com.example.pr2.Repositories.OrderRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", orderRepository.findAll());
        model.addAttribute("modelName", "orders");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Order());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "orders");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Order order) {
        orderRepository.save(order);
        return "redirect:/orders/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", orderRepository.findById(id).orElseThrow());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "orders");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Order order) {
        orderRepository.save(order);
        return "redirect:/orders/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", orderRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "orders");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return "redirect:/orders/view";
    }
}
