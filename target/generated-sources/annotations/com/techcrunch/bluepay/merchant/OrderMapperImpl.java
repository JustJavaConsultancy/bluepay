package com.techcrunch.bluepay.merchant;

import com.techcrunch.bluepay.invoice.Invoice;
import com.techcrunch.bluepay.invoice.InvoiceDTO;
import com.techcrunch.bluepay.product.Product;
import com.techcrunch.bluepay.product.ProductDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-01T13:16:57+0100",
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

        OrderDTO.OrderDTOBuilder orderDTO = OrderDTO.builder();

        orderDTO.id( order.getId() );
        orderDTO.merchantId( order.getMerchantId() );
        orderDTO.invoice( invoiceToInvoiceDTO( order.getInvoice() ) );
        orderDTO.dateCreated( order.getDateCreated() );
        orderDTO.lastUpdated( order.getLastUpdated() );

        return orderDTO.build();
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

    protected Product productDTOToProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDTO.getId() );
        product.setCode( productDTO.getCode() );
        product.setName( productDTO.getName() );
        product.setDescription( productDTO.getDescription() );
        product.setPrice( productDTO.getPrice() );
        product.setQuantityInStock( productDTO.getQuantityInStock() );
        product.setContainsPhysicalGoods( productDTO.getContainsPhysicalGoods() );
        product.setMerchantId( productDTO.getMerchantId() );
        List<String> list = productDTO.getMedia();
        if ( list != null ) {
            product.setMedia( new ArrayList<String>( list ) );
        }
        if ( productDTO.getQuantitySold() != null ) {
            product.setQuantitySold( productDTO.getQuantitySold().intValue() );
        }
        product.setDateCreated( productDTO.getDateCreated() );

        return product;
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
        invoice.setProduct( productDTOToProduct( invoiceDTO.getProduct() ) );

        return invoice;
    }

    protected ProductDTO productToProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO.ProductDTOBuilder productDTO = ProductDTO.builder();

        productDTO.id( product.getId() );
        productDTO.code( product.getCode() );
        productDTO.name( product.getName() );
        productDTO.description( product.getDescription() );
        productDTO.price( product.getPrice() );
        productDTO.dateCreated( product.getDateCreated() );
        productDTO.quantityInStock( product.getQuantityInStock() );
        productDTO.containsPhysicalGoods( product.getContainsPhysicalGoods() );
        List<String> list = product.getMedia();
        if ( list != null ) {
            productDTO.media( new ArrayList<String>( list ) );
        }
        if ( product.getQuantitySold() != null ) {
            productDTO.quantitySold( BigDecimal.valueOf( product.getQuantitySold() ) );
        }
        productDTO.merchantId( product.getMerchantId() );

        return productDTO.build();
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
        invoiceDTO.product( productToProductDTO( invoice.getProduct() ) );

        return invoiceDTO.build();
    }

    protected void productDTOToProduct1(ProductDTO productDTO, Product mappingTarget) {
        if ( productDTO == null ) {
            return;
        }

        if ( productDTO.getId() != null ) {
            mappingTarget.setId( productDTO.getId() );
        }
        if ( productDTO.getCode() != null ) {
            mappingTarget.setCode( productDTO.getCode() );
        }
        if ( productDTO.getName() != null ) {
            mappingTarget.setName( productDTO.getName() );
        }
        if ( productDTO.getDescription() != null ) {
            mappingTarget.setDescription( productDTO.getDescription() );
        }
        if ( productDTO.getPrice() != null ) {
            mappingTarget.setPrice( productDTO.getPrice() );
        }
        if ( productDTO.getQuantityInStock() != null ) {
            mappingTarget.setQuantityInStock( productDTO.getQuantityInStock() );
        }
        if ( productDTO.getContainsPhysicalGoods() != null ) {
            mappingTarget.setContainsPhysicalGoods( productDTO.getContainsPhysicalGoods() );
        }
        if ( productDTO.getMerchantId() != null ) {
            mappingTarget.setMerchantId( productDTO.getMerchantId() );
        }
        if ( mappingTarget.getMedia() != null ) {
            List<String> list = productDTO.getMedia();
            if ( list != null ) {
                mappingTarget.getMedia().clear();
                mappingTarget.getMedia().addAll( list );
            }
        }
        else {
            List<String> list = productDTO.getMedia();
            if ( list != null ) {
                mappingTarget.setMedia( new ArrayList<String>( list ) );
            }
        }
        if ( productDTO.getQuantitySold() != null ) {
            mappingTarget.setQuantitySold( productDTO.getQuantitySold().intValue() );
        }
        if ( productDTO.getDateCreated() != null ) {
            mappingTarget.setDateCreated( productDTO.getDateCreated() );
        }
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
        if ( invoiceDTO.getProduct() != null ) {
            if ( mappingTarget.getProduct() == null ) {
                mappingTarget.setProduct( new Product() );
            }
            productDTOToProduct1( invoiceDTO.getProduct(), mappingTarget.getProduct() );
        }
    }
}
