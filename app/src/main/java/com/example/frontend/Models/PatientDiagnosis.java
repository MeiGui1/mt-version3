package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class PatientDiagnosis {

    @SerializedName("patient_id")
    private int patientId;
    @SerializedName("diagnosistype_id")
    private int diagnosisId;
    private String comment;

    public PatientDiagnosis() {
    }

    public PatientDiagnosis(int patientId, int diagnosisId, String comment) {
        this.patientId = patientId;
        this.diagnosisId = diagnosisId;
        this.comment = comment;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
