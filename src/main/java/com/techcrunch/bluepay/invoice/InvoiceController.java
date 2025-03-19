package com.techcrunch.bluepay.invoice;

import com.techcrunch.bluepay.customer.Customer;
import com.techcrunch.bluepay.customer.CustomerRepository;
import com.techcrunch.bluepay.util.CustomCollectors;
import com.techcrunch.bluepay.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CustomerRepository customerRepository;

    public InvoiceController(final InvoiceService invoiceService,
            final CustomerRepository customerRepository) {
        this.invoiceService = invoiceService;
        this.customerRepository = customerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("statusValues", Status.values());
        model.addAttribute("cusomerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Customer::getId, Customer::getFirstName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("invoices", invoiceService.findAll());
        return "invoice/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("invoice") final InvoiceDTO invoiceDTO) {
        return "invoice/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("invoice") @Valid final InvoiceDTO invoiceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "invoice/add";
        }
        invoiceService.create(invoiceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("invoice.create.success"));
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("invoice", invoiceService.get(id));
        return "invoice/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("invoice") @Valid final InvoiceDTO invoiceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "invoice/edit";
        }
        invoiceService.update(id, invoiceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("invoice.update.success"));
        return "redirect:/invoices";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        invoiceService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("invoice.delete.success"));
        return "redirect:/invoices";
    }

}
