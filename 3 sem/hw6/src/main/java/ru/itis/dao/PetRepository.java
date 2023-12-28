package ru.itis.dao;

import ru.itis.models.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    void save(Pet pet);

    Optional<Pet> getPetById(Long id);

    List<Pet> getAllPets();

    List<Pet> getAllPetsByAccountName(String accountName);

    boolean update(Pet pet);

    boolean delete(Long id);
}
