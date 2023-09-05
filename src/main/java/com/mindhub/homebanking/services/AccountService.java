package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.security.core.Authentication;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface AccountService {

    List<AccountDTO> getAccounts(Authentication authentication);
    Account getAccountById(Long id);
    Account getAcctounByNumber(String numberAccount);
    void saveAccount(Account account);
    boolean controlAccount(Account acctoun);

}
