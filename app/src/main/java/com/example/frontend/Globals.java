package com.example.frontend;

import android.graphics.Bitmap;

public class Globals {
    private static Globals instance;

    //Global variables
    private int fragmentWidth = 200;
    private int fragmentHeight = 200;
    private boolean currentNoteChanged;
    private Bitmap currentNote= Bitmap.createBitmap(200/4*3, 200, Bitmap.Config.ARGB_8888);;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public int getFragmentWidth() {
        return fragmentWidth;
    }

    public void setFragmentWidth(int fragmentWidth) {
        this.fragmentWidth = fragmentWidth;
    }

    public int getFragmentHeight() {
        return fragmentHeight;
    }

    public void setFragmentHeight(int fragmentHeight) {
        this.fragmentHeight = fragmentHeight;
    }

    public boolean getCurrentNoteChanged() {
        return currentNoteChanged;
    }

    public void setCurrentNoteChanged(boolean currentNote) {
        this.currentNoteChanged = currentNote;
    }

    public Bitmap getCurrentNote() {
        return currentNote;
    }

    public void setCurrentNote(Bitmap currentNote) {
        this.currentNote = currentNote;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }


}
