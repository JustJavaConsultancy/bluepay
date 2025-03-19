package com.techcrunch.bluepay.transaction;

import com.techcrunch.bluepay.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(final TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> findAll() {
        final List<Transaction> transactions = transactionRepository.findAll(Sort.by("id"));
        return transactions.stream()
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .toList();
    }

    public TransactionDTO get(final Long id) {
        return transactionRepository.findById(id)
                .map(transaction -> mapToDTO(transaction, new TransactionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TransactionDTO transactionDTO) {
        final Transaction transaction = new Transaction();
        mapToEntity(transactionDTO, transaction);
        return transactionRepository.save(transaction).getId();
    }

    public void update(final Long id, final TransactionDTO transactionDTO) {
        final Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(transactionDTO, transaction);
        transactionRepository.save(transaction);
    }

    public void delete(final Long id) {
        transactionRepository.deleteById(id);
    }

    private TransactionDTO mapToDTO(final Transaction transaction,
            final TransactionDTO transactionDTO) {
        transactionDTO.setId(transaction.getId());
        transactionDTO.setReference(transaction.getReference());
        transactionDTO.setExternalReference(transaction.getExternalReference());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setBeneficiaryAccount(transaction.getBeneficiaryAccount());
        transactionDTO.setSourceAccount(transaction.getSourceAccount());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setPaymentType(transaction.getPaymentType());
        return transactionDTO;
    }

    private Transaction mapToEntity(final TransactionDTO transactionDTO,
            final Transaction transaction) {
        transaction.setReference(transactionDTO.getReference());
        transaction.setExternalReference(transactionDTO.getExternalReference());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setBeneficiaryAccount(transactionDTO.getBeneficiaryAccount());
        transaction.setSourceAccount(transactionDTO.getSourceAccount());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setPaymentType(transactionDTO.getPaymentType());
        return transaction;
    }

    public boolean referenceExists(final String reference) {
        return transactionRepository.existsByReferenceIgnoreCase(reference);
    }

    public boolean externalReferenceExists(final String externalReference) {
        return transactionRepository.existsByExternalReferenceIgnoreCase(externalReference);
    }

    public boolean sourceAccountExists(final String sourceAccount) {
        return transactionRepository.existsBySourceAccountIgnoreCase(sourceAccount);
    }

}
