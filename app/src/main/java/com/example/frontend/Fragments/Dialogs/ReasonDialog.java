package com.example.frontend.Fragments.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.frontend.R;


public class ReasonDialog extends AppCompatDialogFragment {
    private CheckBox cbDrugs;
    private CheckBox cbExercises;
    private CheckBox cbAwareness;
    private CheckBox cbOthers;
    private EditText etOthers;

    public interface ReasonDialogListener {
        void applyTexts(boolean drugsReason, boolean exercisesReason, boolean awarenessReason, boolean otherReasons, String otherReasonsText);
    }

    public ReasonDialogListener listener ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_reason_dialog, null);

        builder.setView(view)
                .setTitle(R.string.improvement_reason)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String otherReasonsText = "";
                        boolean drugsReason = cbDrugs.isChecked();
                        boolean exercisesReason = cbExercises.isChecked();
                        boolean awarenessReason = cbAwareness.isChecked();
                        boolean otherReason = cbOthers.isChecked();
                        if(etOthers.getText()!=null){
                            otherReasonsText = etOthers.getText().toString();
                        }
                        listener.applyTexts(drugsReason,exercisesReason,awarenessReason,otherReason,otherReasonsText);
                    }
                });
        cbDrugs = view.findViewById(R.id.cbDrugs);
        cbExercises = view.findViewById(R.id.cbExercises);
        cbAwareness = view.findViewById(R.id.cbAwareness);
        cbOthers = view.findViewById(R.id.cbOthers);
        etOthers = view.findViewById(R.id.etOtherReason);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ReasonDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ReasonDialogListener ");
        }
    }

}
