package ru.itis.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
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
}
