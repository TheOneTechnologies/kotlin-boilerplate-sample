package com.theonetech.kotlin.domain.utils;

public interface Callback<T> {
    void reply(T response);
}