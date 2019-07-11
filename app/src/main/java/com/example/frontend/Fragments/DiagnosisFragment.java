package com.example.frontend.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.frontend.Models.PatientDrug;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiagnosisFragment extends Fragment {

    private int patientId;
    private View cView;
    private List<String> diagnosisClasses;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagnosis, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        cView = view;
        List<String> exampleClasses = Arrays.asList("Hello", "World!", "Hoi", "Wuhuh");
        addClassButtons();
    }

    private void createClassRadioButtons(List<String> allDiagnosisClasses) {
        final RadioButton[] rb = new RadioButton[5];
        RadioGroup radioGroup = new RadioGroup(getContext());
        radioGroup = (RadioGroup) cView.findViewById(R.id.rgClasses);//create the RadioGroup
        for(String diagnosisClass: allDiagnosisClasses){
            RadioButton radioBtn = new RadioButton(getContext());
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            radioBtn.setLayoutParams(lp);
            radioBtn.setBackgroundResource(R.drawable.radiobutton_selector);
            radioBtn.setGravity(Gravity.CENTER);
            radioBtn.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioBtn.setPadding(0,10,0,10);
            radioBtn.setTextSize(18);
            radioBtn.setLayoutParams(lp);
            radioBtn.setText(diagnosisClass);
            radioBtn.setTextColor(getResources().getColorStateList(R.color.radiobutton_text_selected));
            radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   //Show corresponding Diagnoses in the Scrollview
                }
            });
            radioGroup.addView(radioBtn);

        }
    }

    public void addClassButtons() {
        Call<List<String>> call = jsonPlaceHolderApi.getDiagnosisClasses();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    diagnosisClasses = response.body();
                    createClassRadioButtons(diagnosisClasses);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
