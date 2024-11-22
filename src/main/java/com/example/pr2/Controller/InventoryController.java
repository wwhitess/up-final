package com.example.pr2.Controller;

import com.example.pr2.Model.Inventory;
import com.example.pr2.Repositories.InventoryRepository;
import com.example.pr2.Repositories.VehicleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    private final VehicleRepository vehicleRepository;

    public InventoryController(InventoryRepository inventoryRepository, VehicleRepository vehicleRepository) {
        this.inventoryRepository = inventoryRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", inventoryRepository.findAll());
        model.addAttribute("modelName", "inventory");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Inventory());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "inventory");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Inventory inventory) {
        inventoryRepository.save(inventory);
        return "redirect:/inventory/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("entity", inventoryRepository.findById(id).orElseThrow());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "inventory");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Inventory inventory) {
        inventoryRepository.save(inventory);
        return "redirect:/inventory/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", inventoryRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "inventory");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        inventoryRepository.deleteById(id);
        return "redirect:/inventory/view";
    }
}
