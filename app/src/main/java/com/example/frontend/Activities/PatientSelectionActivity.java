package com.example.frontend.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frontend.Service.JsonPlaceHolderApi;
import com.example.frontend.Models.Patient;
import com.example.frontend.R;

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
    private int columnCounter = 1;
    Context context = this;

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

        //tvPatientlist = (TextView)findViewById(R.id.tvPatientlist);
        addAllPatients();


    }

    public void navigateNextActivity(Patient patient) {
        //jump to Menu
        Intent intent = new Intent(PatientSelectionActivity.this, MenuActivity.class);
        intent.putExtra("patient", patient);
        startActivity(intent);
    }

    public void addAllPatients() {
        Call<List<Patient>> call = jsonPlaceHolderApi.getAllPatients();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (!response.isSuccessful()) {
                    //tvPatientlist.setText(response.code());
                    return;
                } else {
                    allPatients = response.body();

                    for(Patient patient: allPatients){
                        addPatientBtn(patient);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
               // tvPatientlist.setText(t.getMessage());
            }
        });
    }

    public void addPatientBtn(final Patient patient){
        Button btnPatient = new Button(context);
        btnPatient.setText(patient.getShortname());
        btnPatient.setTextSize(24);
        btnPatient.setPadding(0,30,0,30);
        btnPatient.setTransformationMethod(null);
        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateNextActivity(patient);
            }
        });
        LinearLayout ll1 = (LinearLayout) findViewById(R.id.llFirstColumn);
        LinearLayout ll2 = (LinearLayout) findViewById(R.id.llSecondColumn);
        LinearLayout ll3 = (LinearLayout) findViewById(R.id.llThirdColumn);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (columnCounter){
            case 1:
                ll1.addView(btnPatient, lp);
                columnCounter++;
                break;
            case 2:
                ll2.addView(btnPatient, lp);
                columnCounter++;
                break;
            case 3:
                ll3.addView(btnPatient, lp);
                columnCounter = 1;
                break;
        }
    }
}
