package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Account {
    private Long id;
    private String name;
    private List<Pet> pets;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pets=" + pets.stream().map(x -> x.getId() + " " + x.getName()).collect(Collectors.toList()) +
                '}';
    }
}
