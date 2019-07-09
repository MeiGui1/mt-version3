package com.example.frontend.Service;

import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDrug;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("patient")
    Call<List<Patient>> getAllPatients();

    @GET("drugtype")
    Call<List<DrugType>> getAllDrugTypes();

    @GET("patientdrug/{patient_id}")
    Call<List<PatientDrug>> getAllDrugsOfPatient(@Path("patient_id") int patient_id);

    @POST("patientdrug")
    Call<ResponseBody> createPatientDrug(@Body PatientDrug patientDrug);

    @DELETE("patientdrug/{patient_id}/{drugtype_id}")
    Call<ResponseBody> deletePatientDrug(@Path("patient_id") int patient_id, @Path("drugtype_id") int drugtype_id);
}
