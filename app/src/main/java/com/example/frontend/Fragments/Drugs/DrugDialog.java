package com.example.frontend.Fragments.Drugs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.frontend.Models.PatientDrug;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DrugDialog extends AppCompatDialogFragment {
    private EditText etAmount;
    private EditText etDosis;

    public interface DrugDialogListener {
        void applyTexts(String amount, String dosis);
    }

    public DrugDialogListener listener ;


    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_drug_dialog, null);

        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Hinzufügen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String amount = etAmount.getText().toString();
                        String dosis = etDosis.getText().toString();
                        listener.applyTexts(amount,dosis);
                    }
                });
        etAmount = view.findViewById(R.id.edit_amount);
        etDosis = view.findViewById(R.id.edit_dosis);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DrugDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DrugDialogListener ");
        }
    }



}
