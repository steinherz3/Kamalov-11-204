package ru.itis.service;

import ru.itis.models.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Optional<Pet> createPet(String name, Integer age, Double height,
                            String color, String sex, String accountName);

    List<Pet> getAllPets();

    List<Pet> getAllPetsByAccountName(String accountName);

    boolean updatePet(Long id, String petName, String petAge,
                      String petHeight, String petColor, String petSex);

    boolean removePet(Long id);
}
