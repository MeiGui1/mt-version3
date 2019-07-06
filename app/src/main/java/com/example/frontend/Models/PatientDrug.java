package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class PatientDrug {

    @SerializedName("patient_id")
    private int patientId;

    @SerializedName("drug_id")
    private int drugId;

    private String amount;

    private String dosis;

    public int getPatientId() {
        return patientId;
    }

    public int getDrugId() {
        return drugId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDosis() {
        return dosis;
    }
}
