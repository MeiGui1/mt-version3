package com.example.frontend.Service;

import com.example.frontend.Models.DiagnosisType;
import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.ExercisePhoto;
import com.example.frontend.Models.ImprovementReason;
import com.example.frontend.Models.Note;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDiagnosis;
import com.example.frontend.Models.PatientDrug;
import com.example.frontend.Models.PatientExercise;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    //Note Page

    @GET("note/patient_id={patient_id}")
    Call<List<Note>> getAllNotesOfPatient(@Path("patient_id") int patient_id);

    @GET("note/selected/patient_id={patient_id}")
    Call<List<Note>> getSelectedNotesOfPatient(@Path("patient_id") int patient_id);

    @GET("note/{id}")
    Call<Note> getNote(@Path("id") int note_id);

    @GET("note_last_id")
    Call<Integer> getLastNoteId();

    @POST("note")
    Call<ResponseBody> createNote(@Body Note note);

    @DELETE("note/{id}")
    Call<ResponseBody> deleteNote(@Path("id") int note_id);

    @PUT("note/{id}")
    Call<ResponseBody> updateNote(@Path("id") int note_id, @Body Note note);


    //Exercise Page
    @GET("patientexercise/{id}")
    Call<List<PatientExercise>> getSelectedPatientExercises(@Path("id") int patient_id);

    @POST("patientexercise")
    Call<ResponseBody> createPatientExercise(@Body PatientExercise patientExercise);

    @DELETE("patientexercise/{patient_id}/{exercisetype_title}")
    Call<ResponseBody> deletePatientExercise(@Path("patient_id") int patient_id, @Path("exercisetype_title") String exercisetype_title);

    @POST("exercisephoto")
    Call<ResponseBody> createExercisePhoto(@Body ExercisePhoto exercisePhoto);

    @GET("exercisephoto_last_id")
    Call<Integer> getLastPhotoId();

    @GET("exercisephoto/{id}")
    Call<List<ExercisePhoto>> getExercisePhotosOfPatient(@Path("id") int patient_id);

    @DELETE("exercisephoto/{id}")
    Call<ResponseBody> deleteExercisePhoto(@Path("id") int photo_id);


    //PsychoSocial Page
    @POST("psychosocial/reason")
    Call<ResponseBody> createImprovementReason(@Body ImprovementReason improvementReason);

    @PUT("psychosocial/reason/{patient_id}")
    Call<ResponseBody> updateImprovementReason(@Path("patient_id") int patient_id, @Body ImprovementReason improvementReason);

    @GET("psychosocial/reason/{patient_id}")
    Call<ImprovementReason> getImprovementReason(@Path("patient_id") int patient_id);

    @GET("psychosocial/reason/exists/{patient_id}")
    Call<Boolean> existsImprovementReason(@Path("patient_id") int patient_id);
}
