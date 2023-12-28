package ru.itis.util;

import java.util.Arrays;
import java.util.stream.Stream;

public class JsonPresentUtil {
    public static Stream<Integer> getIntStream(String array){

        return Arrays.stream(array.substring(2, array.length() - 2).split(", "))
                .map(Integer::parseInt);
    }
}
