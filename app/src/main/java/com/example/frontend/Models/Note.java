package com.example.frontend.Models;

import com.google.gson.annotations.SerializedName;

public class Note {

    private int id;

    @SerializedName("patient_id")
    private int patientId;

    @SerializedName("note_bytes")
    private byte[] noteBytes;

    private boolean selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public byte[] getNoteBytes() {
        return noteBytes;
    }

    public void setNoteBytes(byte[] noteBytes) {
        this.noteBytes = noteBytes;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
