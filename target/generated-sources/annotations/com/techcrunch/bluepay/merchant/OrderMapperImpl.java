package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.invoice.Invoice;
import com.techcrunch.bluepay.invoice.InvoiceDTO;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-31T10:51:19+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setId( orderDTO.getId() );
        order.setMerchantId( orderDTO.getMerchantId() );
        order.setInvoice( invoiceDTOToInvoice( orderDTO.getInvoice() ) );
        order.setDateCreated( orderDTO.getDateCreated() );
        order.setLastUpdated( orderDTO.getLastUpdated() );

        return order;
    }

    @Override
    public OrderDTO toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        Long id = null;
        String merchantId = null;
        InvoiceDTO invoice = null;
        OffsetDateTime dateCreated = null;
        OffsetDateTime lastUpdated = null;

        id = order.getId();
        merchantId = order.getMerchantId();
        invoice = invoiceToInvoiceDTO( order.getInvoice() );
        dateCreated = order.getDateCreated();
        lastUpdated = order.getLastUpdated();

        OrderDTO orderDTO = new OrderDTO( id, merchantId, invoice, dateCreated, lastUpdated );

        return orderDTO;
    }

    @Override
    public Order partialUpdate(OrderDTO orderDTO, Order order) {
        if ( orderDTO == null ) {
            return order;
        }

        if ( orderDTO.getId() != null ) {
            order.setId( orderDTO.getId() );
        }
        if ( orderDTO.getMerchantId() != null ) {
            order.setMerchantId( orderDTO.getMerchantId() );
        }
        if ( orderDTO.getInvoice() != null ) {
            if ( order.getInvoice() == null ) {
                order.setInvoice( new Invoice() );
            }
            invoiceDTOToInvoice1( orderDTO.getInvoice(), order.getInvoice() );
        }
        if ( orderDTO.getDateCreated() != null ) {
            order.setDateCreated( orderDTO.getDateCreated() );
        }
        if ( orderDTO.getLastUpdated() != null ) {
            order.setLastUpdated( orderDTO.getLastUpdated() );
        }

        return order;
    }

    protected Invoice invoiceDTOToInvoice(InvoiceDTO invoiceDTO) {
        if ( invoiceDTO == null ) {
            return null;
        }

        Invoice invoice = new Invoice();

        invoice.setId( invoiceDTO.getId() );
        invoice.setDescription( invoiceDTO.getDescription() );
        invoice.setCustomerEmail( invoiceDTO.getCustomerEmail() );
        invoice.setCustomerName( invoiceDTO.getCustomerName() );
        invoice.setMerchantId( invoiceDTO.getMerchantId() );
        invoice.setCustomerPhoneNumber( invoiceDTO.getCustomerPhoneNumber() );
        invoice.setIssueDate( invoiceDTO.getIssueDate() );
        invoice.setDueDate( invoiceDTO.getDueDate() );
        invoice.setAmount( invoiceDTO.getAmount() );
        invoice.setStatus( invoiceDTO.getStatus() );
        invoice.setDateCreated( invoiceDTO.getDateCreated() );
        invoice.setLastUpdated( invoiceDTO.getLastUpdated() );

        return invoice;
    }

    protected InvoiceDTO invoiceToInvoiceDTO(Invoice invoice) {
        if ( invoice == null ) {
            return null;
        }

        InvoiceDTO.InvoiceDTOBuilder invoiceDTO = InvoiceDTO.builder();

        invoiceDTO.id( invoice.getId() );
        invoiceDTO.description( invoice.getDescription() );
        invoiceDTO.customerEmail( invoice.getCustomerEmail() );
        invoiceDTO.customerName( invoice.getCustomerName() );
        invoiceDTO.merchantId( invoice.getMerchantId() );
        invoiceDTO.customerPhoneNumber( invoice.getCustomerPhoneNumber() );
        invoiceDTO.issueDate( invoice.getIssueDate() );
        invoiceDTO.dueDate( invoice.getDueDate() );
        invoiceDTO.amount( invoice.getAmount() );
        invoiceDTO.status( invoice.getStatus() );
        invoiceDTO.dateCreated( invoice.getDateCreated() );
        invoiceDTO.lastUpdated( invoice.getLastUpdated() );

        return invoiceDTO.build();
    }

    protected void invoiceDTOToInvoice1(InvoiceDTO invoiceDTO, Invoice mappingTarget) {
        if ( invoiceDTO == null ) {
            return;
        }

        if ( invoiceDTO.getId() != null ) {
            mappingTarget.setId( invoiceDTO.getId() );
        }
        if ( invoiceDTO.getDescription() != null ) {
            mappingTarget.setDescription( invoiceDTO.getDescription() );
        }
        if ( invoiceDTO.getCustomerEmail() != null ) {
            mappingTarget.setCustomerEmail( invoiceDTO.getCustomerEmail() );
        }
        if ( invoiceDTO.getCustomerName() != null ) {
            mappingTarget.setCustomerName( invoiceDTO.getCustomerName() );
        }
        if ( invoiceDTO.getMerchantId() != null ) {
            mappingTarget.setMerchantId( invoiceDTO.getMerchantId() );
        }
        if ( invoiceDTO.getCustomerPhoneNumber() != null ) {
            mappingTarget.setCustomerPhoneNumber( invoiceDTO.getCustomerPhoneNumber() );
        }
        if ( invoiceDTO.getIssueDate() != null ) {
            mappingTarget.setIssueDate( invoiceDTO.getIssueDate() );
        }
        if ( invoiceDTO.getDueDate() != null ) {
            mappingTarget.setDueDate( invoiceDTO.getDueDate() );
        }
        if ( invoiceDTO.getAmount() != null ) {
            mappingTarget.setAmount( invoiceDTO.getAmount() );
        }
        if ( invoiceDTO.getStatus() != null ) {
            mappingTarget.setStatus( invoiceDTO.getStatus() );
        }
        if ( invoiceDTO.getDateCreated() != null ) {
            mappingTarget.setDateCreated( invoiceDTO.getDateCreated() );
        }
        if ( invoiceDTO.getLastUpdated() != null ) {
            mappingTarget.setLastUpdated( invoiceDTO.getLastUpdated() );
        }
    }
}
