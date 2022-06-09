package com.grupo5.huiapi.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.CustomException;

import java.util.List;

public interface Service<T, I> {
    T get(I id) throws CustomException;
    List<T> getAll();
    String insert(JsonNode object) throws CustomException, JsonProcessingException;
    String update(I id, String password, JsonNode object) throws CustomException, JsonProcessingException;
    String delete(I id, String password) throws CustomException;
}
