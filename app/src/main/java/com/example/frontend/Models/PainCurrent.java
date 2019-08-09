package com.example.frontend.Models;

import java.io.Serializable;

public class PainCurrent implements Serializable {
    private int patient_id;
    private int intensity;
    private byte[] location_teeth;
    private byte[] location_face_left;
    private byte[] location_face_right;
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
        return location_teeth;
    }

    public void setLocation_teeth(byte[] location_teeth) {
        this.location_teeth = location_teeth;
    }

    public byte[] getLocation_face_left() {
        return location_face_left;
    }

    public void setLocation_face_left(byte[] location_face_left) {
        this.location_face_left = location_face_left;
    }

    public byte[] getLocation_face_right() {
        return location_face_right;
    }

    public void setLocation_face_right(byte[] location_face_right) {
        this.location_face_right = location_face_right;
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
