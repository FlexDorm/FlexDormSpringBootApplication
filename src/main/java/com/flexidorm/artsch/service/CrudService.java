package com.flexidorm.artsch.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T>{
    T save (T t) throws Exception; //add o update
    void delete (Long id) throws  Exception;//delete
    List<T> getAll() throws Exception;//Get all elements
    Optional<T> getById(Long id) throws Exception;//get element by id
}
