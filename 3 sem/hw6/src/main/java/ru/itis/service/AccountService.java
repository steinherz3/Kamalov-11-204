package ru.itis.service;

import ru.itis.models.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> signIn(String name);

    Optional<Account> getAccountByName(String name);

    Optional<Account> getAccountByNameWithPets(String name);
}
