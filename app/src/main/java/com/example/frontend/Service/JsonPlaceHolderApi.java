package com.example.frontend.Service;

import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDrug;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("patient")
    Call<List<Patient>> getAllPatients();

    @GET("drugtype")
    Call<List<DrugType>> getAllDrugTypes();

    @GET("patientdrug/{patient_id}")
    Call<List<PatientDrug>> getAllDrugsOfPatient(@Path("patient_id") int patient_id);

}
