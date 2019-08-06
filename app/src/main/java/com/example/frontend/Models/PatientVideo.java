package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class PatientVideo {

    @SerializedName("patient_id")
    private int patientId;

    @SerializedName("video_path")
    private String videoPath;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
