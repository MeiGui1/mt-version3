package com.example.frontend.Models;

import android.util.Base64;

import java.io.Serializable;

public class PainCurrent implements Serializable {
    private int patient_id;
    private int intensity;
    private String location_teeth;
    private String location_face_left;
    private String location_face_right;
    private String pain_pattern;
    private boolean dull;
    private boolean pulling;
    private boolean stinging;
    private boolean pulsating;
    private boolean burning;
    private boolean pinsneedles;
    private boolean tingling;
    private boolean numb;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public byte[] getLocation_teeth() {
        byte[] bytes = Base64.decode(location_teeth, Base64.NO_WRAP);
        return bytes;
    }

    public void setLocation_teeth(byte[] location_teeth) {
        String string = Base64.encodeToString(location_teeth, Base64.NO_WRAP);
        this.location_teeth = string;
    }

    public byte[] getLocation_face_left() {
        byte[] bytes = Base64.decode(location_face_left, Base64.NO_WRAP);
        return bytes;
    }

    public void setLocation_face_left(byte[] location_face_left) {
        String string = Base64.encodeToString(location_face_left, Base64.NO_WRAP);
        this.location_face_left = string;
    }

    public byte[] getLocation_face_right() {
        byte[] bytes = Base64.decode(location_face_right, Base64.NO_WRAP);
        return bytes;
    }

    public void setLocation_face_right(byte[] location_face_right) {
        String string = Base64.encodeToString(location_face_right, Base64.NO_WRAP);
        this.location_face_right = string;
    }

    public String getPain_pattern() {
        return pain_pattern;
    }

    public void setPain_pattern(String pain_pattern) {
        this.pain_pattern = pain_pattern;
    }

    public boolean isDull() {
        return dull;
    }

    public void setDull(boolean dull) {
        this.dull = dull;
    }

    public boolean isPulling() {
        return pulling;
    }

    public void setPulling(boolean pulling) {
        this.pulling = pulling;
    }

    public boolean isStinging() {
        return stinging;
    }

    public void setStinging(boolean stinging) {
        this.stinging = stinging;
    }

    public boolean isPulsating() {
        return pulsating;
    }

    public void setPulsating(boolean pulsating) {
        this.pulsating = pulsating;
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public boolean isPinsneedles() {
        return pinsneedles;
    }

    public void setPinsneedles(boolean pinsneedles) {
        this.pinsneedles = pinsneedles;
    }

    public boolean isTingling() {
        return tingling;
    }

    public void setTingling(boolean tingling) {
        this.tingling = tingling;
    }

    public boolean isNumb() {
        return numb;
    }

    public void setNumb(boolean numb) {
        this.numb = numb;
    }
}
