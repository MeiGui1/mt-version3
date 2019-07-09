package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class PatientDrug {

    @SerializedName("patient_id")
    private int patientId;

    @SerializedName("drugtype_id")
    private int drugId;

    private String amount;

    private String dosis;

    public int getPatientId() {
        return patientId;
    }

    public int getDrugTypeId() {
        return drugId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDosis() {
        return dosis;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }
}
