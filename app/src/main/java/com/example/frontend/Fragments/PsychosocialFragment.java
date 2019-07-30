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
import com.example.frontend.R;

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

    private boolean reasonDrugs;
    private boolean reasonExercises;
    private boolean reasonAwareness;
    private boolean reasonOthers;
    private String reasonOthersText;





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
        reasonDialog.setTargetFragment(PsychosocialFragment.this, 1);
        reasonDialog.show(getActivity().getSupportFragmentManager(), "Reason Dialog");
    }

    @Override
    public void applyTexts(boolean drugsReason, boolean exercisesReason, boolean awarenessReason, boolean otherReasons, String otherReasonsText) {
        String test = String.valueOf(drugsReason);
        Toast.makeText(getActivity(), test, Toast.LENGTH_LONG).show();

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
}
