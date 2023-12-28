package ru.itis.service.impl;

import ru.itis.dao.AccountRepository;
import ru.itis.dao.impl.AccountRepositorySingleTonImpl;
import ru.itis.models.Account;
import ru.itis.models.Pet;
import ru.itis.service.AccountService;
import ru.itis.service.PetService;

import java.util.List;
import java.util.Optional;

public class AccountServiceSingleTonImpl implements AccountService {

    private static AccountServiceSingleTonImpl instance;
    private AccountRepository accountRepository;

    private PetService petService;
    private AccountServiceSingleTonImpl(AccountRepository accountRepository, PetService petService){
        this.accountRepository = accountRepository;
        this.petService = petService;
    }

    public static AccountServiceSingleTonImpl getInstance(){
        if(instance == null){
            instance = new AccountServiceSingleTonImpl(AccountRepositorySingleTonImpl.getInstance(),
                    PetServiceSingleTonImpl.getInstance());
        }
        return instance;
    }

    @Override
    public Optional<Account> signIn(String name) {
        Optional<Account> result = accountRepository.getAccountByName(name);
        if(result.isEmpty()){
            Account account = Account.builder()
                    .name(name)
                    .build();
            accountRepository.save(account);

            return Optional.of(account);
        }
        return result;
    }

    @Override
    public Optional<Account> getAccountByName(String name) {
        return accountRepository.getAccountByName(name);
    }

    @Override
    public Optional<Account> getAccountByNameWithPets(String name) {
        Account account = getAccountByName(name).orElseThrow(IllegalArgumentException::new);
        List<Pet> accountPets = petService.getAllPetsByAccountName(name);

        accountPets.forEach(x -> account.getPets().add(x));

        return Optional.of(account);
    }
}
