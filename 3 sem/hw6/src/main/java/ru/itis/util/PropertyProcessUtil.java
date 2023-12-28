package ru.itis.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyProcessUtil {
    public static Properties getProperties(String path){
        Properties properties = new Properties();

        try(FileReader fileReader = new FileReader(path)){
            properties.load(fileReader);
        }
        catch (IOException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return properties;
    }
}
