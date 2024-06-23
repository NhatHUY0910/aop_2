package com.practice_aop_2.service;

import com.practice_aop_2.exception.DuplicateEmailException;

import java.util.List;

public interface IGeneralService<T> {
    List<T> findAll();
    void save(T t) throws DuplicateEmailException;
    T findById(Long id);
    void delete(Long id);
}
