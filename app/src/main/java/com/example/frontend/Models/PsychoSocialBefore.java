package com.example.frontend.Models;

import java.io.Serializable;

public class PsychoSocialBefore implements Serializable {

    private int patient_id;

    private int pain_xpos;
    private int pain_ypos;

    private int family_xpos;
    private int family_ypos;

    private int work_xpos;
    private int work_ypos;

    private int finance_xpos;
    private int finance_ypos;

    private int event_xpos;
    private int event_ypos;

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPain_xpos() {
        return pain_xpos;
    }

    public void setPain_xpos(int pain_xpos) {
        this.pain_xpos = pain_xpos;
    }

    public int getPain_ypos() {
        return pain_ypos;
    }

    public void setPain_ypos(int pain_ypos) {
        this.pain_ypos = pain_ypos;
    }

    public int getFamily_xpos() {
        return family_xpos;
    }

    public void setFamily_xpos(int family_xpos) {
        this.family_xpos = family_xpos;
    }

    public int getFamily_ypos() {
        return family_ypos;
    }

    public void setFamily_ypos(int family_ypos) {
        this.family_ypos = family_ypos;
    }

    public int getWork_xpos() {
        return work_xpos;
    }

    public void setWork_xpos(int work_xpos) {
        this.work_xpos = work_xpos;
    }

    public int getWork_ypos() {
        return work_ypos;
    }

    public void setWork_ypos(int work_ypos) {
        this.work_ypos = work_ypos;
    }

    public int getFinance_xpos() {
        return finance_xpos;
    }

    public void setFinance_xpos(int finance_xpos) {
        this.finance_xpos = finance_xpos;
    }

    public int getFinance_ypos() {
        return finance_ypos;
    }

    public void setFinance_ypos(int finance_ypos) {
        this.finance_ypos = finance_ypos;
    }

    public int getEvent_xpos() {
        return event_xpos;
    }

    public void setEvent_xpos(int event_xpos) {
        this.event_xpos = event_xpos;
    }

    public int getEvent_ypos() {
        return event_ypos;
    }

    public void setEvent_ypos(int event_ypos) {
        this.event_ypos = event_ypos;
    }
}
