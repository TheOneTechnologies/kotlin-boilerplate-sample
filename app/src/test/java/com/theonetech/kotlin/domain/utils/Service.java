package com.theonetech.kotlin.domain.utils;

import retrofit2.Callback;
import retrofit2.Response;

public interface Service {
    void doAction(String request, Callback<Response> callback);
}