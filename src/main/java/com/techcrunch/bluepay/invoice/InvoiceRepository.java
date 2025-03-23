package com.techcrunch.bluepay.invoice;

import com.techcrunch.bluepay.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    //Invoice findFirstByCusomer(Customer customer);

}
