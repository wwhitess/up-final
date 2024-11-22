package com.example.pr2.Controller;

import com.example.pr2.Model.OrderDetail;
import com.example.pr2.Repositories.OrderDetailRepository;
import com.example.pr2.Repositories.OrderRepository;
import com.example.pr2.Repositories.VehicleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order_details")
public class OrderDetailController {

    private final OrderDetailRepository orderDetailRepository;

    private final OrderRepository orderRepository;

    private final VehicleRepository vehicleRepository;

    public OrderDetailController(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, VehicleRepository vehicleRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", orderDetailRepository.findAll());
        model.addAttribute("modelName", "order_details");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new OrderDetail());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "order_details");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
        return "redirect:/order_details/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", orderDetailRepository.findById(id).orElseThrow());
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "order_details");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
        return "redirect:/order_details/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", orderDetailRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "order_details");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderDetailRepository.deleteById(id);
        return "redirect:/order_details/view";
    }
}
