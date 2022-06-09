package com.grupo5.huiapi.utils;

import com.grupo5.huiapi.modules.category.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    /**
     * Adds a variable list of items to a list and returns the result
     * @param list
     * @param items
     * @param <T>
     * @return
     */
    public static<T> List<T> add(List<T> list, T ...items) {
        list.addAll(List.of(items));
        return list;
    }

    /**
     * Adds a variable list of lists and returns the concatenaded one
     * @param lists
     * @param <T>
     * @return
     */
    public static<T> List<T> concat(List<T>... lists)
    {
        List<T> result = new ArrayList<>();

        for (List<T> l: lists) {
            result.addAll(l);
        }

        return result;
    }

    /**
     * Searchs a String in an array and returns it, if it doesn't find it returns null
     * @param list
     * @param searching
     * @return
     */
    public static String getString(List<String> list, String searching) {
        for (String s : list)
            if (s.contains(searching))
                return s;
        return null;
    }

    public static Category getCategoryByName(List<Category> categories, String name) {
        for (Category cat : categories)
            if (cat.getName().equals(name))
                return cat;
        return null;
    }
}
