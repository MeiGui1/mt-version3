package com.example.frontend.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.frontend.R;

import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

public class TranslatorFragment extends Fragment {

    private int patientId;
    private Button btnGoogleTranslate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_translator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnGoogleTranslate = (Button) view.findViewById(R.id.btnGoogleTranslate);
        btnGoogleTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getActivity().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.translate");
                startActivity(i);
            }
        });

    }
}

