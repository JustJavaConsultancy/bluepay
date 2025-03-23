package com.techcrunch.bluepay.accounting;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public AccountDTO get(String id) {
        return accountMapper.toDto(accountRepository.findById(id).orElseThrow());
    }

    public AccountDTO create(AccountDTO accountDTO) {
        return accountMapper.toDto(accountRepository.save(accountMapper.toEntity(accountDTO)));
    }

    public AccountDTO update(String id, AccountDTO accountDTO) {
        return accountMapper.toDto(accountRepository.save(accountMapper.partialUpdate(accountDTO, accountRepository.findById(id).orElseThrow())));
    }
    public void delete(String id) {
        accountRepository.deleteById(id);
    }

    //https://www.figma.com/design/V5qWvkeZqmjA5Yba6tiCXL/Bluepay-Payment-Gateway?node-id=581-31601&t=2yTtSmT65RaG6ZIB-1

}
