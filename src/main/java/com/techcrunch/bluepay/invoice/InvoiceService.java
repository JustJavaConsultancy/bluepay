package com.techcrunch.bluepay.invoice;

import com.techcrunch.bluepay.customer.Customer;
import com.techcrunch.bluepay.customer.CustomerRepository;
import com.techcrunch.bluepay.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceService(final InvoiceRepository invoiceRepository,
            final CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public List<InvoiceDTO> findAll() {
        final List<Invoice> invoices = invoiceRepository.findAll(Sort.by("id"));
        return invoices.stream()
                .map(invoice -> mapToDTO(invoice, new InvoiceDTO()))
                .toList();
    }

    public InvoiceDTO get(final Long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> mapToDTO(invoice, new InvoiceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InvoiceDTO invoiceDTO) {
        final Invoice invoice = new Invoice();
        mapToEntity(invoiceDTO, invoice);
        return invoiceRepository.save(invoice).getId();
    }

    public void update(final Long id, final InvoiceDTO invoiceDTO) {
        final Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(invoiceDTO, invoice);
        invoiceRepository.save(invoice);
    }

    public void delete(final Long id) {
        invoiceRepository.deleteById(id);
    }

    private InvoiceDTO mapToDTO(final Invoice invoice, final InvoiceDTO invoiceDTO) {
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setNumber(invoice.getNumber());
        invoiceDTO.setIssueDate(invoice.getIssueDate());
        invoiceDTO.setDueDate(invoice.getDueDate());
        invoiceDTO.setAmount(invoice.getAmount());
        invoiceDTO.setStatus(invoice.getStatus());
        invoiceDTO.setCusomer(invoice.getCusomer() == null ? null : invoice.getCusomer().getId());
        return invoiceDTO;
    }

    private Invoice mapToEntity(final InvoiceDTO invoiceDTO, final Invoice invoice) {
        invoice.setNumber(invoiceDTO.getNumber());
        invoice.setIssueDate(invoiceDTO.getIssueDate());
        invoice.setDueDate(invoiceDTO.getDueDate());
        invoice.setAmount(invoiceDTO.getAmount());
        invoice.setStatus(invoiceDTO.getStatus());
        final Customer cusomer = invoiceDTO.getCusomer() == null ? null : customerRepository.findById(invoiceDTO.getCusomer())
                .orElseThrow(() -> new NotFoundException("cusomer not found"));
        invoice.setCusomer(cusomer);
        return invoice;
    }

}
