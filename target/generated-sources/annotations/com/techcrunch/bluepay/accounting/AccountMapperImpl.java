package com.techcrunch.bluepay.accounting;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-26T15:53:14+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account account = new Account();

        account.setId( accountDTO.getId() );
        account.setName( accountDTO.getName() );
        account.setCode( accountDTO.getCode() );
        account.setType( accountDTO.getType() );
        account.setCurrency( accountDTO.getCurrency() );
        account.setBalance( accountDTO.getBalance() );

        return account;
    }

    @Override
    public AccountDTO toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        String id = null;
        String name = null;
        String code = null;
        String type = null;
        String currency = null;
        BigDecimal balance = null;

        id = account.getId();
        name = account.getName();
        code = account.getCode();
        type = account.getType();
        currency = account.getCurrency();
        balance = account.getBalance();

        AccountDTO accountDTO = new AccountDTO( id, name, code, type, currency, balance );

        return accountDTO;
    }

    @Override
    public Account partialUpdate(AccountDTO accountDTO, Account account) {
        if ( accountDTO == null ) {
            return account;
        }

        if ( accountDTO.getId() != null ) {
            account.setId( accountDTO.getId() );
        }
        if ( accountDTO.getName() != null ) {
            account.setName( accountDTO.getName() );
        }
        if ( accountDTO.getCode() != null ) {
            account.setCode( accountDTO.getCode() );
        }
        if ( accountDTO.getType() != null ) {
            account.setType( accountDTO.getType() );
        }
        if ( accountDTO.getCurrency() != null ) {
            account.setCurrency( accountDTO.getCurrency() );
        }
        if ( accountDTO.getBalance() != null ) {
            account.setBalance( accountDTO.getBalance() );
        }

        return account;
    }
}
