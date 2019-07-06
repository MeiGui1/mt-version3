package com.example.frontend.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.Service.JsonPlaceHolderApi;
import com.example.frontend.Models.Patient;
import com.example.frontend.R;
import com.example.frontend.Service.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatientSelectionActivity extends AppCompatActivity {
    private String username = "";
    private TextView tvPatientlist;
    private List<Patient> allPatients;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_selection);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("usernameKey");
        }

        tvPatientlist = (TextView)findViewById(R.id.tvPatientlist);

        addAllPatients();

    }

    public void nextActivity(View view) {
        //jump to Menu for faster testing puroposes
        Intent intent = new Intent(PatientSelectionActivity.this, MenuActivity.class);
        intent.putExtra("usernameKey", username);
        startActivity(intent);
    }

    public void addAllPatients() {
        Call<List<Patient>> call = jsonPlaceHolderApi.getAllPatients();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (!response.isSuccessful()) {
                    tvPatientlist.setText(response.code());
                    return;
                } else {
                    allPatients = response.body();
                    String string = "";
                    for(Patient patient: allPatients){
                        string = string + "ID: " + patient.getId() + "\n Name:" + patient.getShortname() + "\n Gender:" + patient.getGender() + "\n\n";
                    }
                    tvPatientlist.setText(string);
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                tvPatientlist.setText(t.getMessage());
            }
        });
    }
}
