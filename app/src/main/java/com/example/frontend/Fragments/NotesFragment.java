package com.example.frontend.Fragments;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.frontend.Fragments.Notes.PaintView;
import com.example.frontend.R;

import java.io.ByteArrayOutputStream;

public class NotesFragment extends Fragment {

    private PaintView paintView;
    private View cView;
    private int patientId;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        patientId = getArguments().getInt("patientId");
        return inflater.inflate(R.layout.fragment_notes,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.post(new Runnable() {
                    public void run() {
                        cView = view;
                        paintView = (PaintView) view.findViewById(R.id.paintView);
                        linearLayout = (LinearLayout) cView.findViewById(R.id.linearLayout);
                        DisplayMetrics metrics = new DisplayMetrics();
                        metrics.heightPixels = view.getHeight();
                        metrics.widthPixels = view.getWidth();
                        paintView.init(metrics);
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pen_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
            case R.id.save:
                byte[] savedByte = bitmapToByte(paintView.getBitmap());
                addByteArrayToView(savedByte);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] bitmapToByte(Bitmap drawing)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        drawing.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] drawingByteArray = bos.toByteArray();
        return drawingByteArray;
    }

    public void addByteArrayToView(byte[] drawing)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(drawing, 0, drawing.length);
        final ImageView image = new ImageView(getContext());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        param.setMargins(0,0,0,20);
        image.setLayoutParams(param);
        image.setAdjustViewBounds(true);
        image.setPadding(5,5,5,5);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image.isSelected()){
                    image.setSelected(false);
                    image.setBackgroundColor(0);
                    Toast.makeText(getActivity(), "onClick successful", Toast.LENGTH_SHORT).show();
                }else{
                    image.setSelected(true);
                    image.setBackgroundColor(getResources().getColor(R.color.colorDarkBlue));
                    Toast.makeText(getActivity(), "onClick successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        image.setImageBitmap(bmp);
        linearLayout.addView(image);
    }
}
