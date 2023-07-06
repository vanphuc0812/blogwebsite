package com.example.blogwebsite.common.service;


import com.example.blogwebsite.common.model.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenericService<T extends BaseEntity, D, I> {
    JpaRepository<T, I> getRepository(); // Factory Method

    ModelMapper getMapper();

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default List<T> findAll(Pageable page) {
        return getRepository().findAll(page)
                .stream().toList();
    }

    default List<D> findAllDto(Class<D> clazz) {
        return getRepository().findAll().stream()
                .map(model -> getMapper().map(model, clazz))
                .toList();
    }


    default Page<List<D>> findAllDto(Pageable page, Class<D> clazz) {

        Page<T> modelPage = getRepository().findAll(page);
        List<D> dtoList = modelPage.stream()
                .map(model -> getMapper().map(model, clazz))
                .toList();
        return (Page<List<D>>) new PageImpl<>(dtoList, page, modelPage.getTotalElements());
    }


    default List<T> findByIds(List<I> ids) {
        return getRepository().findAllById(ids);
    }

    default Optional<T> findById(I id) {
        return getRepository().findById(id);
    }

    default T save(T entity) {
        return getRepository().save(entity);
    }

    default T save(D dto, Class<T> modelClass) {
        T model = getMapper().map(dto, modelClass);
        return getRepository().save(model);
    }


    default D save(D dto, Class<T> modelClass, Class<D> dtoClass) {
        T model = getMapper().map(dto, modelClass);
        T savedModel = getRepository().save(model);
        return getMapper().map(savedModel, dtoClass);
    }

    default D save(T entity, Class<T> modelClass, Class<D> dtoClass) {
        T savedModel = getRepository().save(entity);
        return getMapper().map(savedModel, dtoClass);
    }

    default void deleteById(I id) {
        getRepository().deleteById(id);
    }

    default T update(T entity) {
        return getRepository().save(entity);
    }

}
