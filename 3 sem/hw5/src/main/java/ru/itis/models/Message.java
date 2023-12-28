package ru.itis.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Message {
    private String value;
    private Account sender;
    private Account receiver;
}