package ru.itis.dao;

import ru.itis.models.Account;

import java.util.Optional;

public interface AccountRepository {
    void save(Account account);
    Optional<Account> getAccountByName(String name);
}
