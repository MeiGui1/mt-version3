package com.example.frontend.Fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.ScrollView;

import com.example.frontend.Fragments.Notes.PaintView;
import com.example.frontend.R;

public class NotesFragment extends Fragment {

    private PaintView paintView;
    private View cView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
                LinearLayout linearLayout1 = (LinearLayout) cView.findViewById(R.id.linearLayout);
                ImageView image = new ImageView(getContext());
                Bitmap saved = paintView.getBitmap();
                image.setImageBitmap(saved);
                linearLayout1.addView(image);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
