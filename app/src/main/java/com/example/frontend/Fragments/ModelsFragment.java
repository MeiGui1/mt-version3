package com.example.frontend.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frontend.R;

public class ModelsFragment extends Fragment {
    private int patientId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        return inflater.inflate(R.layout.fragment_models,container,false);
    }
}
