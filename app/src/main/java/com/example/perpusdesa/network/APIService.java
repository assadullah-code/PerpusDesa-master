package com.example.perpusdesa.network;

import com.example.perpusdesa.model.PepusModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {

    @GET("/books")
    Call<List<PepusModel>> getPerpusList();
    @GET
    Call<String> getPDF(@Url String url);

}
