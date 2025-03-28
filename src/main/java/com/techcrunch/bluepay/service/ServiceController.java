package com.techcrunch.bluepay.service;

import com.techcrunch.bluepay.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(final ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("services", serviceService.findAll());
        return "service/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("service") final ServiceDTO serviceDTO) {
        return "service/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("service") @Valid final ServiceDTO serviceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "service/add";
        }
        serviceService.create(serviceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("service.create.success"));
        return "redirect:/services";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("service", serviceService.get(id));
        return "service/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("service") @Valid final ServiceDTO serviceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "service/edit";
        }
        serviceService.update(id, serviceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("service.update.success"));
        return "redirect:/services";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        serviceService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("service.delete.success"));
        return "redirect:/services";
    }

}
