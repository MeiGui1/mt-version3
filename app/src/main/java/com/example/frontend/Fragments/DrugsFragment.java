package com.example.frontend.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.TooltipCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.frontend.Fragments.Dialogs.DrugDialog;
import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.PatientDrug;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DrugsFragment extends Fragment implements DrugDialog.DrugDialogListener {
    private View cView;
    private List<DrugType> allDrugTypes = new ArrayList<>();
    private List<PatientDrug> allDrugsOfPatient = new ArrayList<>();
    private List<Integer> allDrugIdsOfPatient = new ArrayList<>();
    private PatientDrug selectedPatientDrug = new PatientDrug();
    private Button selectedDrugButton;
    private int patientId;
    private int columnCounter = 1;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drugs, container, false);

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        cView = view;
        selectedDrugButton = new Button(getContext());
        addDrugButtons(patientId);
    }

    public void addAllDrugsTypes() {
        Call<List<DrugType>> call = jsonPlaceHolderApi.getAllDrugTypes();
        call.enqueue(new Callback<List<DrugType>>() {
            @Override
            public void onResponse(Call<List<DrugType>> call, Response<List<DrugType>> response) {
                if (!response.isSuccessful()) {
                    return;
                } else {
                    allDrugTypes = response.body();

                    for (DrugType drugType : allDrugTypes) {
                        addDrugTypeBtn(drugType);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DrugType>> call, Throwable t) {
            }
        });
    }

    public void addDrugTypeBtn(final DrugType drugType) {
        final Button btnDrugType = new Button(getContext());
        btnDrugType.setText(drugType.getName());
        TooltipCompat.setTooltipText(btnDrugType.getRootView(), drugType.getDescription());
        btnDrugType.setTextSize(18);
        btnDrugType.setPadding(0, 30, 0, 30);
        btnDrugType.setBackgroundResource(R.drawable.button_selector_effect);
        btnDrugType.setTransformationMethod(null);
        if (allDrugIdsOfPatient.contains(drugType.getId())) {
            btnDrugType.setSelected(true);
        }

        btnDrugType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnDrugType.isSelected()) {
                    deletePatientDrug(patientId, drugType.getId());
                    btnDrugType.setSelected(false);
                } else {
                    selectedPatientDrug.setPatientId(patientId);
                    selectedPatientDrug.setDrugId(drugType.getId());
                    selectedDrugButton = btnDrugType;
                    openDrugDialog();
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
                ll1.addView(btnDrugType, lp);
                columnCounter++;
                break;
            case 2:
                ll2.addView(btnDrugType, lp);
                columnCounter++;
                break;
            case 3:
                ll3.addView(btnDrugType, lp);
                columnCounter = 1;
                break;
        }
    }

    public void addDrugButtons(final int patientId) {
        //Get all PatientDrugs of Patient
        Call<List<PatientDrug>> call = jsonPlaceHolderApi.getAllDrugsOfPatient(patientId);
        call.enqueue(new Callback<List<PatientDrug>>() {
            @Override
            public void onResponse(Call<List<PatientDrug>> call, Response<List<PatientDrug>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allDrugsOfPatient = response.body();

                    for (PatientDrug patientDrug : allDrugsOfPatient) {
                        int id = patientDrug.getDrugTypeId();
                        allDrugIdsOfPatient.add(patientDrug.getDrugTypeId());
                    }
                    //Add all Buttons
                    addAllDrugsTypes();
                }
            }

            @Override
            public void onFailure(Call<List<PatientDrug>> call, Throwable t) {
                // tvPatientlist.setText(t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addNewPatientDrug(PatientDrug patientDrug) {
        Call<ResponseBody> call = jsonPlaceHolderApi.createPatientDrug(patientDrug);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void deletePatientDrug(int patientId, int drugtypeId) {
        Call<ResponseBody> call = jsonPlaceHolderApi.deletePatientDrug(patientId, drugtypeId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public void openDrugDialog() {
        DrugDialog drugDialog = new DrugDialog();
        drugDialog.setTargetFragment(DrugsFragment.this, 1);
        drugDialog.show(getActivity().getSupportFragmentManager(), "Drug Dialog");
    }

    @Override
    public void applyTexts(String amount, String dosis) {
        if (!amount.isEmpty()) {
            selectedPatientDrug.setAmount(amount);
        }
        if (!dosis.isEmpty()) {
            selectedPatientDrug.setDosis(dosis);
        }
        addNewPatientDrug(selectedPatientDrug);
        selectedDrugButton.setSelected(true);
    }
}
