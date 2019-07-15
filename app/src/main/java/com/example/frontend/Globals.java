package com.example.frontend;

import android.graphics.Bitmap;

public class Globals {
    private static Globals instance;

    //Global variables
    private int fragmentWidth = 200;
    private int fragmentHeight = 200;

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

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }


}
