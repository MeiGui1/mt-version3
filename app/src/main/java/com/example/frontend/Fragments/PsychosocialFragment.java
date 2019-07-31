package com.example.frontend.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.frontend.Fragments.Dialogs.DrugDialog;
import com.example.frontend.Fragments.Dialogs.ReasonDialog;
import com.example.frontend.Models.ImprovementReason;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PsychosocialFragment extends Fragment implements ReasonDialog.ReasonDialogListener {

    private int patientId;

    private Button btnPainBefore;
    private Button btnFamiliyBefore;
    private Button btnWorkBefore;
    private Button btnFinancialBefore;
    private Button btnEventBefore;

    private Button btnPainAfter;
    private Button btnFamiliyAfter;
    private Button btnWorkAfter;
    private Button btnFinancialAfter;
    private Button btnEventAfter;

    private ImageView btnReason;

    private int xDelta;
    private int yDelta;
    private RelativeLayout rlActual;

    private ImprovementReason improvementReasonOfPatient = new ImprovementReason();

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_psychosocial, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rlActual = view.findViewById(R.id.rlBefore);

        btnPainBefore = view.findViewById(R.id.btnPainBefore);
        btnPainBefore.setOnTouchListener(new ChoiceTouchListener());
        btnFamiliyBefore = view.findViewById(R.id.btnFamilyBefore);
        btnFamiliyBefore.setOnTouchListener(new ChoiceTouchListener());
        btnWorkBefore = view.findViewById(R.id.btnWorkBefore);
        btnWorkBefore.setOnTouchListener(new ChoiceTouchListener());
        btnFinancialBefore = view.findViewById(R.id.btnFinancialBefore);
        btnFinancialBefore.setOnTouchListener(new ChoiceTouchListener());
        btnEventBefore = view.findViewById(R.id.btnEventBefore);
        btnEventBefore.setOnTouchListener(new ChoiceTouchListener());

        btnPainAfter = view.findViewById(R.id.btnPainAfter);
        btnPainAfter.setOnTouchListener(new ChoiceTouchListener());
        btnFamiliyAfter = view.findViewById(R.id.btnFamilyAfter);
        btnFamiliyAfter.setOnTouchListener(new ChoiceTouchListener());
        btnWorkAfter = view.findViewById(R.id.btnWorkAfter);
        btnWorkAfter.setOnTouchListener(new ChoiceTouchListener());
        btnFinancialAfter = view.findViewById(R.id.btnFinancialAfter);
        btnFinancialAfter.setOnTouchListener(new ChoiceTouchListener());
        btnEventAfter = view.findViewById(R.id.btnEventAfter);
        btnEventAfter.setOnTouchListener(new ChoiceTouchListener());

        btnReason = view.findViewById(R.id.btnReason);
        btnReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReasonDialog();
            }
        });
    }

    private final class ChoiceTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int X = (int) motionEvent.getRawX();
            int Y = (int) motionEvent.getRawY();
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    //TODO: PUT REQUEST
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    int rlWidth = rlActual.getWidth()-70;
                    int rlHeight = rlActual.getHeight()-70;

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    int newX = X - xDelta;
                    int newY = Y - yDelta;
                    if ((newX >= 0 && newX <= rlWidth && newY >= 0 && newY <= rlHeight)){
                        layoutParams.leftMargin = newX;
                        layoutParams.topMargin = newY;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        view.setLayoutParams(layoutParams);
                    }
                    break;
            }
            rlActual.invalidate();
            return true;
        }
    }

    public void openReasonDialog() {
        ReasonDialog reasonDialog = new ReasonDialog();
        Bundle args = new Bundle();
        args.putInt("patient_id", patientId);
        reasonDialog.setArguments(args);
        reasonDialog.setTargetFragment(PsychosocialFragment.this, 1);
        reasonDialog.show(getActivity().getSupportFragmentManager(), "Reason Dialog");
    }

    @Override
    public void applyTexts(boolean drugsReason, boolean exercisesReason, boolean awarenessReason, boolean otherReasons, String otherReasonsText) {
        improvementReasonOfPatient.setPatient_id(patientId);
        improvementReasonOfPatient.setDrugs(drugsReason);
        improvementReasonOfPatient.setExercises(exercisesReason);
        improvementReasonOfPatient.setAwareness(awarenessReason);
        improvementReasonOfPatient.setOther_reason(otherReasons);
        improvementReasonOfPatient.setOther_reason_text(otherReasonsText);

        setImprovementReason();
        /*
        if (!amount.isEmpty()) {
            selectedPatientDrug.setAmount(amount);
        }
        if (!dosis.isEmpty()) {
            selectedPatientDrug.setDosis(dosis);
        }
        addNewPatientDrug(selectedPatientDrug);
        selectedDrugButton.setSelected(true);
        */
    }

    public void addNewImprovementReason(ImprovementReason improvementReason) {
        Call<ResponseBody> call = jsonPlaceHolderApi.createImprovementReason(improvementReason);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "createNote NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateImprovementReason(final ImprovementReason updatedImprovementReason) {
        Call<ResponseBody> call = jsonPlaceHolderApi.updateImprovementReason(patientId, updatedImprovementReason);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), "createImprovementReason NOT successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setImprovementReason() {
        Call<Boolean> call = jsonPlaceHolderApi.existsImprovementReason(patientId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Get improvement reason not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean improvementReasonExists = response.body();
                    
                    if(improvementReasonExists){
                        updateImprovementReason(improvementReasonOfPatient);
                    }else {
                        addNewImprovementReason(improvementReasonOfPatient);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
