package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplements implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientService clienteService;

    @Override
    public List<AccountDTO> getAccounts(Authentication authentication){
        return clienteService.getCurrent(authentication).getAccounts().stream().collect(toList());
    }
    @Override
    public Account getAccountById(Long id){
        Account account = accountRepository.findById(id).orElse(null);
        return account;
    }
    @Override
    public Account getAcctounByNumber(String numberAccount){
        Account account = accountRepository.findByNumber(numberAccount);
        return account;
    }
    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }
    @Override
    public boolean  controlAccount(Account account){
        boolean result=true;
        if(account==null){
            result=false;
        }
        return result;
    }

}
