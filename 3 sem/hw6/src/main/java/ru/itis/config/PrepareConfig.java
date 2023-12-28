package ru.itis.config;

import ru.itis.dao.SimpleDataSourceSingleton;
import ru.itis.util.PropertyProcessUtil;

public class PrepareConfig {

    private final static String PROPERTIES_PATH = "src/main/resources/application.properties";
    public static void prepareDataSource(){
        SimpleDataSourceSingleton.getInstance(PropertyProcessUtil.getProperties(PROPERTIES_PATH));
    }
}
