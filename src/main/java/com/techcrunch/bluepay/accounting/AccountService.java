package com.techcrunch.bluepay.accounting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcrunch.bluepay.invoice.InvoiceDTO;
import com.techcrunch.bluepay.invoice.Status;
import com.techcrunch.bluepay.merchant.MerchantDto;
import com.techcrunch.bluepay.transaction.PaymentType;
import com.techcrunch.bluepay.transaction.TransactionDTO;
import com.techcrunch.bluepay.transaction.TransactionService;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service("accountService")
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionService transactionService;
    @Autowired
    ObjectMapper objectMapper;
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper, TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.transactionService = transactionService;
    }

    public AccountDTO get(String id) {
        return accountMapper.toDto(accountRepository.findById(id).orElseThrow());
    }

    public AccountDTO create(AccountDTO accountDTO) {
        return accountMapper.toDto(accountRepository.save(accountMapper.toEntity(accountDTO)));
    }

    public AccountDTO getByCode(String code) {
        return accountMapper.toDto(accountRepository.findByCode(code).orElseThrow());
    }
    public void customerPaymentJournalEntry(DelegateExecution execution) {
        Map<String,Object> variables = execution.getVariables();
        System.out.println(" The execution in customerPaymentJournalEntry==="+variables);
        InvoiceDTO invoiceDTO=objectMapper.convertValue(variables.get("invoice"),InvoiceDTO.class);
        TransactionDTO transactionDTO=TransactionDTO.builder()
                .amount(invoiceDTO.getAmount())
                .beneficiaryAccount("Payment Gateway Account")
                .externalReference(invoiceDTO.getId().toString())
                .paymentType(PaymentType.INFLOW)
                .channel(variables.get("channel").toString())
                .sourceAccount(invoiceDTO.getCustomerName())
                .status(Status.PAID)
                .build();
        System.out.println(" The execution==="+execution);
    }
    public AccountDTO update(String id, AccountDTO accountDTO) {
        return accountMapper.toDto(accountRepository.save(accountMapper.partialUpdate(accountDTO, accountRepository.findById(id).orElseThrow())));
    }
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    public void createMerchantRelevantAccounts(MerchantDto merchantDto,
                                               Map<String, Object> bankDetail){
        AccountDTO receivable = AccountDTO.builder()
                .name(merchantDto.getBusinessName()+" Receivable")
                .balance(new BigDecimal(0.00))
                .code("receivable")
                .currency("NGN")
                .type("CHART_OF_ACCOUNTS")
                .ownerId(merchantDto.getId().toString())
                .build();
        AccountDTO payable = AccountDTO.builder()
                .name(merchantDto.getBusinessName()+" Payable")
                .balance(new BigDecimal(0.00))
                .code("payable")
                .currency("NGN")
                .type("CHART_OF_ACCOUNTS")
                .ownerId(merchantDto.getId().toString())
                .build();
        AccountDTO bankAccount  = AccountDTO.builder()
                .name((String) bankDetail.get("accName"))
                .balance(new BigDecimal(0.00))
                .code((String) bankDetail.get("bankName"))
                .currency("NGN")
                .type("BANK")
                .accNumber((String) bankDetail.get("accNumber"))
                .ownerId(merchantDto.getId().toString())
                .build();

        create(receivable,payable,bankAccount);
    }

    private void create(AccountDTO receivable, AccountDTO payable, AccountDTO bankAccount) {
        accountRepository.save(accountMapper.toEntity(receivable));
        accountRepository.save(accountMapper.toEntity(payable));
        accountRepository.save(accountMapper.toEntity(bankAccount));
    }

}
