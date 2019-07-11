package com.example.frontend.Service;

import com.example.frontend.Models.DiagnosisType;
import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDiagnosis;
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


    //Drug Page

    @GET("drugtype")
    Call<List<DrugType>> getAllDrugTypes();

    @GET("patientdrug/{patient_id}")
    Call<List<PatientDrug>> getAllDrugsOfPatient(@Path("patient_id") int patient_id);

    @POST("patientdrug")
    Call<ResponseBody> createPatientDrug(@Body PatientDrug patientDrug);

    @DELETE("patientdrug/{patient_id}/{drugtype_id}")
    Call<ResponseBody> deletePatientDrug(@Path("patient_id") int patient_id, @Path("drugtype_id") int drugtype_id);


    //Diagnosis Page

    @GET("diagnosistype/type")
    Call<List<String>> getDiagnosisClasses();

    @GET("diagnosistype/type={type}")
    Call<List<DiagnosisType>> getAllDiagnosisTypesOfClass(@Path("type") String type);

    @GET("patientdiagnosis/{patient_id}/type={type}")
    Call<List<PatientDiagnosis>> getPatientDiagnosesOfClass(@Path("patient_id") int patient_id, @Path("type") String type);

    @POST("patientdiagnosis")
    Call<ResponseBody> createPatientDiagnosis(@Body PatientDiagnosis patientDiagnosis);

    @DELETE("patientdiagnosis/{patient_id}/{diagnosistype_id}")
    Call<ResponseBody> deletePatientDiagnosis(@Path("patient_id") int patient_id, @Path("diagnosistype_id") int diagnosistype_id);


}
