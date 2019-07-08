package com.example.frontend.Service;

import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("patient")
    Call<List<Patient>> getAllPatients();

    @GET("drug/drugtype")
    Call<List<DrugType>> getAllDrugTypes();
}
