package com.example.server.service;

public interface DtoService<T> {

    T convertToDto(Object object);

}
