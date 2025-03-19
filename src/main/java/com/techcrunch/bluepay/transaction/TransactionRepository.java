package com.techcrunch.bluepay.transaction;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByReferenceIgnoreCase(String reference);

    boolean existsByExternalReferenceIgnoreCase(String externalReference);

    boolean existsBySourceAccountIgnoreCase(String sourceAccount);

}
