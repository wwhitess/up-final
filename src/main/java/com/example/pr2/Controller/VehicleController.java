package com.example.pr2.Controller;

import com.example.pr2.Model.Vehicle;
import com.example.pr2.Repositories.ManufacturerRepository;
import com.example.pr2.Repositories.VehicleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final ManufacturerRepository manufacturerRepository;

    public VehicleController(VehicleRepository vehicleRepository, ManufacturerRepository manufacturerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_USER')")
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("entities", vehicleRepository.findAll());
        model.addAttribute("modelName", "vehicles");
        return "view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entity", new Vehicle());
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("modelType", "add");
        model.addAttribute("modelName", "vehicles");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/add")
    public String add(@ModelAttribute("entity") Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        model.addAttribute("entity", vehicle);
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        model.addAttribute("modelType", "edit");
        model.addAttribute("modelName", "vehicles");
        return "add_update";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/update")
    public String update(@ModelAttribute("entity") Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles/view";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("entity", vehicleRepository.findById(id).orElseThrow());
        model.addAttribute("modelName", "vehicles");
        return "delete";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        vehicleRepository.deleteById(id);
        return "redirect:/vehicles/view";
    }
}
