package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class PatientDrug {

    @SerializedName("patient_id")
    private int patientId;
    @SerializedName("drugtype_id")
    private int drugId;

    public PatientDrug() {
    }

    public PatientDrug(int patientId, int drugId, String amount, String dosis) {
        this.patientId = patientId;
        this.drugId = drugId;
        this.amount = amount;
        this.dosis = dosis;
    }

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

    public String getDosisInText() {
        String dosisInText = "";
        if (dosis != null && !dosis.isEmpty()) {
            if (dosis.charAt(0) == 'i') {
                dosisInText = "morgens";
            }
            if (dosis.charAt(1) == '1') {
                if(!dosisInText.equals(""))
                {
                    dosisInText = dosisInText + "-";
                }
                dosisInText = dosisInText + "mittags";
            }
            if (dosis.charAt(2) == '1') {
                if(!dosisInText.equals(""))
                {
                    dosisInText = dosisInText + "-";
                }
                dosisInText = dosisInText + "abends";
            }
            if (dosis.charAt(3) == '1') {
                if(!dosisInText.equals(""))
                {
                    dosisInText = dosisInText + "-";
                }
                dosisInText = dosisInText + "nachts";
            }
        }
        return dosisInText;
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
