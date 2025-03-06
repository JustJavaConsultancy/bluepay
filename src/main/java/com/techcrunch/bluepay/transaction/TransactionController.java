package com.techcrunch.bluepay.transaction;

import com.techcrunch.bluepay.invoice.Status;
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
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("statusValues", Status.values());
        model.addAttribute("paymentTypeValues", PaymentType.values());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        return "transaction/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("transaction") final TransactionDTO transactionDTO) {
        return "transaction/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("transaction") @Valid final TransactionDTO transactionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaction/add";
        }
        transactionService.create(transactionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaction.create.success"));
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("transaction", transactionService.get(id));
        return "transaction/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("transaction") @Valid final TransactionDTO transactionDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "transaction/edit";
        }
        transactionService.update(id, transactionDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("transaction.update.success"));
        return "redirect:/transactions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        transactionService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("transaction.delete.success"));
        return "redirect:/transactions";
    }

}
