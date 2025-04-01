package com.techcrunch.bluepay.transaction;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    boolean existsByReferenceIgnoreCase(String reference);

    boolean existsByExternalReferenceIgnoreCase(String externalReference);

    boolean existsBySourceAccountIgnoreCase(String sourceAccount);

    List<Transaction> findByTransactionOwnerIgnoreCase(String transactionOwner);

    Transaction findTransactionById(Long id);
}
