package ru.itis.models;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Pet {
    private Long id;

    private String name;

    private Integer age;

    private Double height;

    private String color;

    private Sex sex;

    private Account account;

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", color='" + color + '\'' +
                ", sex=" + sex +
                ", account=" + account.getName() +
                '}';
    }

    public enum Sex{
        MALE,
        FEMALE
    }
}
