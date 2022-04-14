package com.grupo5.huiapi.utils;

import java.util.Locale;

public class StringUtils {
    public static String capitalize(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase(Locale.ROOT);
    }
}
