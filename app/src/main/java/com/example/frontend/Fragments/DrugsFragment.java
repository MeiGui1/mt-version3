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
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Patient;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DrugsFragment extends Fragment {
    private View cView;
    private List<DrugType> allDrugTypes;
    private int columnCounter = 1;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drugs, container, false);

    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        /*view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.post(new Runnable() {
                    public void run() {

                    }
                });
            }
        });*/
        cView = view;
        addAllDrugsTypes();
    }
    public void addAllDrugsTypes() {
        Call<List<DrugType>> call = jsonPlaceHolderApi.getAllDrugTypes();
        call.enqueue(new Callback<List<DrugType>>() {
            @Override
            public void onResponse(Call<List<DrugType>> call, Response<List<DrugType>> response) {
                if (!response.isSuccessful()) {
                    //tvPatientlist.setText(response.code());
                    return;
                } else {
                    allDrugTypes = response.body();

                    for(DrugType drugType: allDrugTypes){
                        addDrugTypeBtn(drugType);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DrugType>> call, Throwable t) {
                // tvPatientlist.setText(t.getMessage());
            }
        });
    }
    public void addDrugTypeBtn(final DrugType drugType){
        Button btnDrugType = new Button(getContext());
        btnDrugType.setText(drugType.getName());
        TooltipCompat.setTooltipText(btnDrugType.getRootView(), drugType.getDescription());
        btnDrugType.setTextSize(18);
        btnDrugType.setPadding(0,30,0,30);
        btnDrugType.setBackgroundResource(R.drawable.button_selector_effect);
        btnDrugType.setTransformationMethod(null);
        btnDrugType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: select the drug
                showPatientDrugPopup();
            }
        });
        LinearLayout ll1 = (LinearLayout) cView.findViewById(R.id.llFirstColumn);
        LinearLayout ll2 = (LinearLayout) cView.findViewById(R.id.llSecondColumn);
        LinearLayout ll3 = (LinearLayout) cView.findViewById(R.id.llThirdColumn);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,20,10,20);
        switch (columnCounter){
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

    public void showPatientDrugPopup() {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_drug_selection, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(cView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
