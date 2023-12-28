package ru.itis.util;

import ru.itis.models.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;


public class HTMLReaderUtil {

    public static String readHTML(Path htmlPath){

        try(BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(htmlPath)))){
            StringBuilder html = new StringBuilder();
            for (String line : reader.lines().collect(Collectors.toList())) {
                html.append(line);
            }
            return html.toString();
        }
        catch (IOException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String readHTMLAndAddMessagesTable(Path htmlPath, List<Message> messages){
        try(BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(htmlPath)))){
            StringBuilder html = new StringBuilder();
            for (String line : reader.lines().collect(Collectors.toList())) {
                if(line.contains("<tbody></tbody>")){
                    html.append(insertMessages(line, messages));
                }
                else
                    html.append(line);
            }
            return html.toString();
        }
        catch (IOException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    private static String insertMessages(String line, List<Message> messages){
        String mainString = line.trim();
        String start = mainString.substring(0, 7);
        String end = mainString.substring(7);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        for (Message message : messages) {
            //language=HTML
            stringBuilder.append("<tr>" +
                    "<td>" + message.getSender().getName() + "</td>" +
                    "<td>" + message.getValue() + "</td>" +
                    "</tr>");
        }
        return stringBuilder.append(end).toString();
    }
}
