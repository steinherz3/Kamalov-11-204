package ru.itis.service.impl;

import ru.itis.dao.AccountRepository;
import ru.itis.dao.PetRepository;
import ru.itis.dao.impl.AccountRepositorySingleTonImpl;
import ru.itis.dao.impl.PetRepositorySingleTonImpl;
import ru.itis.models.Account;
import ru.itis.models.Pet;
import ru.itis.service.PetService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PetServiceSingleTonImpl implements PetService {

    private static PetServiceSingleTonImpl instance;
    private PetRepository petRepository;
    private AccountRepository accountRepository;

    private PetServiceSingleTonImpl(PetRepository petRepository, AccountRepository accountRepository){
        this.petRepository = petRepository;
        this.accountRepository = accountRepository;
    }

    public static PetServiceSingleTonImpl getInstance(){
        if(instance == null){
            instance = new PetServiceSingleTonImpl(PetRepositorySingleTonImpl.getInstance(),
                    AccountRepositorySingleTonImpl.getInstance());
        }
        return instance;
    }
    @Override
    public Optional<Pet> createPet(String name, Integer age, Double height, String color, String sex, String accountName) {
        Account account = accountRepository.getAccountByName(accountName)
                .orElseThrow(IllegalArgumentException::new);

        Pet pet = Pet.builder()
                .name(name)
                .age(age)
                .height(height)
                .color(color)
                .sex(sexStringToEnum.apply(sex))
                .account(account)
                .build();

        petRepository.save(pet);
        return Optional.of(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.getAllPets();
    }

    @Override
    public List<Pet> getAllPetsByAccountName(String accountName) {
        return petRepository.getAllPetsByAccountName(accountName);
    }

    @Override
    public boolean updatePet(Long id, String petName, String petAge,
                             String petHeight, String petColor, String petSex) {
        Pet pet = Pet.builder()
                .id(id)
                .build();

        if(!petName.isEmpty())
            pet.setName(petName);
        if(!petAge.isEmpty())
            pet.setAge(Integer.parseInt(petAge));
        if(!petHeight.isEmpty())
            pet.setHeight(Double.parseDouble(petHeight));
        if(!petColor.isEmpty())
            pet.setColor(petColor);
        if(!(petSex == null || petSex.isEmpty()))
            pet.setSex(sexStringToEnum.apply(petSex));

        return petRepository.update(pet);
    }

    @Override
    public boolean removePet(Long id) {
        return petRepository.delete(id);
    }


    private final Function<String, Pet.Sex> sexStringToEnum = str -> {
        if(str.toLowerCase().equals("male"))
            return Pet.Sex.MALE;
        else if (str.toLowerCase().equals("female"))
            return Pet.Sex.FEMALE;
        else
            throw new IllegalArgumentException("Not a sex");
    };
}