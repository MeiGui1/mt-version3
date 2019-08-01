package com.example.frontend.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.TooltipCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.frontend.Fragments.Dialogs.DiagnosisDialog;
import com.example.frontend.Models.DiagnosisType;
import com.example.frontend.Models.PatientDiagnosis;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiagnosisFragment extends Fragment implements DiagnosisDialog.DiagnosisDialogListener {

    private int patientId;
    private View cView;
    private List<String> diagnosisClasses = new ArrayList<>();
    private List<DiagnosisType> allDiagnosisTypesOfClass = new ArrayList<>();
    private List<Integer> allPatientDiagnosisIdsOfClass = new ArrayList<>();
    private List<PatientDiagnosis> allPatientDiagnosisOfClass = new ArrayList<>();
    private PatientDiagnosis selectedPatientDiagnosis = new PatientDiagnosis();
    private int columnCounter = 1;
    private Button selectedDiagnosisBtn;

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
        selectedDiagnosisBtn = new Button(getContext());
        addClassButtons();

}

    public void addClassButtons() {
        Call<List<String>> call = jsonPlaceHolderApi.getDiagnosisClasses();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "GetDiagnosisClasses not successful", Toast.LENGTH_SHORT).show();
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

    private void createClassRadioButtons(List<String> allDiagnosisClasses) {
        boolean firstRadioBtn = true;
        RadioGroup radioGroup = new RadioGroup(getContext());
        radioGroup = (RadioGroup) cView.findViewById(R.id.rgClasses);
        for (String diagnosisClass : allDiagnosisClasses) {
            final RadioButton radioBtn = new RadioButton(getContext());
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            radioBtn.setLayoutParams(lp);
            radioBtn.setBackgroundResource(R.drawable.radiobutton_selector);
            radioBtn.setGravity(Gravity.CENTER);
            radioBtn.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioBtn.setPadding(0, 10, 0, 10);
            radioBtn.setTextSize(18);
            radioBtn.setSingleLine(true);
            radioBtn.setLayoutParams(lp);
            radioBtn.setText(diagnosisClass);
            radioBtn.setTextColor(getResources().getColorStateList(R.color.radiobutton_text_selected));
            radioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allPatientDiagnosisIdsOfClass.clear();
                    addDiagnosisButtons((String)radioBtn.getText());
                }
            });
            radioGroup.addView(radioBtn);
        }
    }

    public void addDiagnosisButtons(final String selectedClass) {
        //Get all PatientDiagnoses of Patient
        Call<List<PatientDiagnosis>> call = jsonPlaceHolderApi.getPatientDiagnosesOfClass(patientId, selectedClass);
        call.enqueue(new Callback<List<PatientDiagnosis>>() {
            @Override
            public void onResponse(Call<List<PatientDiagnosis>> call, Response<List<PatientDiagnosis>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "GetPatientDiagnosesOfClass not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    removeAllDiagnosisBtns();
                    allPatientDiagnosisOfClass = response.body();

                    for (PatientDiagnosis patientDiagnosis : allPatientDiagnosisOfClass) {
                        allPatientDiagnosisIdsOfClass.add(patientDiagnosis.getDiagnosisId());
                    }
                    //Add all Buttons
                    addAllDiagnosisTypes(selectedClass);
                }
            }

            @Override
            public void onFailure(Call<List<PatientDiagnosis>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addAllDiagnosisTypes(String selectedClass) {
        Call<List<DiagnosisType>> call = jsonPlaceHolderApi.getAllDiagnosisTypesOfClass(selectedClass);
        call.enqueue(new Callback<List<DiagnosisType>>() {
            @Override
            public void onResponse(Call<List<DiagnosisType>> call, Response<List<DiagnosisType>> response) {
                if (!response.isSuccessful()) {
                    return;
                } else {
                    allDiagnosisTypesOfClass = response.body();
                    for (DiagnosisType diagnosisType : allDiagnosisTypesOfClass) {
                        addDiagnosisTypeBtn(diagnosisType);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DiagnosisType>> call, Throwable t) {
            }
        });
    }

    public void addDiagnosisTypeBtn(final DiagnosisType diagnosisType) {
        final Button btnDiagnosisType = new Button(getContext());
        btnDiagnosisType.setText(diagnosisType.getName());
        TooltipCompat.setTooltipText(btnDiagnosisType.getRootView(), diagnosisType.getDescription());
        btnDiagnosisType.setTextSize(16);
//        btnDiagnosisType.setSingleLine(true);
        btnDiagnosisType.setHeight(140);
        btnDiagnosisType.setPadding(20, 20, 20, 20);
        btnDiagnosisType.setBackgroundResource(R.drawable.button_selector_effect);
        btnDiagnosisType.setTransformationMethod(null);
        if (allPatientDiagnosisIdsOfClass.contains(diagnosisType.getId())) {
            btnDiagnosisType.setSelected(true);
            btnDiagnosisType.setTextColor(Color.WHITE);
        }
        btnDiagnosisType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnDiagnosisType.isSelected()) {
                    deletePatientDiagnosis(patientId, diagnosisType.getId());
                    btnDiagnosisType.setSelected(false);
                    btnDiagnosisType.setTextColor(Color.BLACK);
                } else {
                    selectedPatientDiagnosis.setPatientId(patientId);
                    selectedPatientDiagnosis.setDiagnosisId(diagnosisType.getId());
                    selectedDiagnosisBtn = btnDiagnosisType;
                    openDiagnosisDialog();
                }
            }
        });
        LinearLayout ll1 = (LinearLayout) cView.findViewById(R.id.llFirstColumn);
        LinearLayout ll2 = (LinearLayout) cView.findViewById(R.id.llSecondColumn);
        LinearLayout ll3 = (LinearLayout) cView.findViewById(R.id.llThirdColumn);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 20, 10, 20);
        switch (columnCounter) {
            case 1:
                ll1.addView(btnDiagnosisType, lp);
                columnCounter++;
                break;
            case 2:
                ll2.addView(btnDiagnosisType, lp);
                columnCounter++;
                break;
            case 3:
                ll3.addView(btnDiagnosisType, lp);
                columnCounter = 1;
                break;
        }
    }

    public void addNewPatientDiagnosis(final PatientDiagnosis patientDiagnosis) {
        Call<ResponseBody> call = jsonPlaceHolderApi.createPatientDiagnosis(patientDiagnosis);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getActivity(), "createPatientDiagnosis successful "+patientDiagnosis.getPatientId() + patientDiagnosis.getDiagnosisId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "createPatientDiagnosis NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletePatientDiagnosis(int patientId, int diagnosistypeId) {
        Call<ResponseBody> call = jsonPlaceHolderApi.deletePatientDiagnosis(patientId, diagnosistypeId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void removeAllDiagnosisBtns(){
        LinearLayout ll1 = (LinearLayout) cView.findViewById(R.id.llFirstColumn);
        LinearLayout ll2 = (LinearLayout) cView.findViewById(R.id.llSecondColumn);
        LinearLayout ll3 = (LinearLayout) cView.findViewById(R.id.llThirdColumn);
        if(((LinearLayout) ll1).getChildCount() > 0)
            ((LinearLayout) ll1).removeAllViews();
        if(((LinearLayout) ll2).getChildCount() > 0)
            ((LinearLayout) ll2).removeAllViews();
        if(((LinearLayout) ll3).getChildCount() > 0)
            ((LinearLayout) ll3).removeAllViews();
        columnCounter = 1;
    }

    public void openDiagnosisDialog() {
        DiagnosisDialog diagnosisDialog = new DiagnosisDialog();
        diagnosisDialog.setTargetFragment(DiagnosisFragment.this, 1);
        diagnosisDialog.show(getActivity().getSupportFragmentManager(), "Diagnosis Dialog");
    }

    @Override
    public void applyTexts(String comment) {
        if (!comment.isEmpty()) {
            selectedPatientDiagnosis.setComment(comment);
        }
        addNewPatientDiagnosis(selectedPatientDiagnosis);
        selectedDiagnosisBtn.setSelected(true);
        selectedDiagnosisBtn.setTextColor(Color.WHITE);
    }
}
