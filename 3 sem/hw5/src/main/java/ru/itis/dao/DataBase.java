package ru.itis.dao;

import ru.itis.models.Account;
import ru.itis.models.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataBase {

    private static List<Account> accounts = new ArrayList<>();
    private static List<Message> messages = new ArrayList<>();

    public static boolean addAccount(String name){
        return accounts.add(Account.builder()
                        .name(name)
                        .messages(new ArrayList<>())
                        .build());

    }
    public static boolean addMessage(String messageText, Account sender, Account receiver){
        Message message = Message.builder()
                .value(messageText)
                .sender(sender)
                .receiver(receiver)
                .build();
        receiver.getMessages().add(message);
        return messages.add(message);
    }

    public static boolean hasAccount(String name){
        return accounts.stream()
                .anyMatch(x -> x.getName().toLowerCase()
                        .equals(name.toLowerCase()));
    }

    public static Optional<Account> getAccount(String name){
        return accounts.stream()
                .filter(x -> x.getName().toLowerCase().equals(name.toLowerCase()))
                .reduce((a, b) -> b);
    }

    public static List<Message> getAllMessagesByReceiver(String receiverName){
        return messages.stream()
                .filter(x -> x.getReceiver().getName().toLowerCase().equals(receiverName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<Message> getAllMessages(){
        return messages;
    }

    public static List<Account> getAllAccounts(){
        return accounts;
    }
}
