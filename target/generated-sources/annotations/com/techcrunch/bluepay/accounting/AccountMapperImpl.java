package com.techcrunch.bluepay.accounting;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-31T21:49:45+0100",
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
        account.setOwnerId( accountDTO.getOwnerId() );
        account.setAccNumber( accountDTO.getAccNumber() );

        return account;
    }

    @Override
    public AccountDTO toDto(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO.AccountDTOBuilder accountDTO = AccountDTO.builder();

        accountDTO.id( account.getId() );
        accountDTO.name( account.getName() );
        accountDTO.code( account.getCode() );
        accountDTO.type( account.getType() );
        accountDTO.currency( account.getCurrency() );
        accountDTO.balance( account.getBalance() );
        accountDTO.ownerId( account.getOwnerId() );
        accountDTO.accNumber( account.getAccNumber() );

        return accountDTO.build();
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
        if ( accountDTO.getOwnerId() != null ) {
            account.setOwnerId( accountDTO.getOwnerId() );
        }
        if ( accountDTO.getAccNumber() != null ) {
            account.setAccNumber( accountDTO.getAccNumber() );
        }

        return account;
    }
}
