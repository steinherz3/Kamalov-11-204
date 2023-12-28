package ru.itis.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = "messages")
public class Account {
    private String name;
    private List<Message> messages;
}
