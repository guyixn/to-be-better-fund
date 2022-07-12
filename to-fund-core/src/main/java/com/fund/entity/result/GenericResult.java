package com.fund.entity.result;

public class GenericResult<T> {
    private T data;

    public static <T> T success(PageResult data) {
        return (T) data;
    }

    public static <T> T success(Result data) {
        return (T) data;
    }
}
