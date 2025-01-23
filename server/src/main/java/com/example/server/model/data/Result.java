package com.example.server.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {

    private final boolean success;

    private final String message;

    private final T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(true, null, data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(false, message, null);
    }

}
