package com.example.frontend.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.frontend.R;

public class ModelsFragment extends Fragment {
    private int patientId;
    private WebView modelView;
    private String currentUrl;
    private ImageView ivKauen;
    private ImageView ivQuerschnitt;

    private View.OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        patientId = getArguments().getInt("patientId");
        View v = inflater.inflate(R.layout.fragment_models, container, false);
        modelView = (WebView) v.findViewById(R.id.modelView);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.iv_kauen:
                        currentUrl = "https://human.biodigital.com/widget/?be=2vwf&ui-annotations=true&ui-info=true&ui-zoom=true&ui-share=true&uaid=4Aty9&lang=de";
                        break;
                    case R.id.iv_querschnitt:
                        currentUrl = "https://human.biodigital.com/widget/?be=31qc&ui-tools=true&ui-dissect=true&ui-isolate=true&ui-xray=true&ui-cross-section=false&ui-annotations=true&ui-info=true&ui-zoom=true&ui-share=true&uaid=4AuuL&lang=de";
                        break;
                }
                modelView.loadUrl(currentUrl);
                // Enable Javascript
                WebSettings webSettings = modelView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                // Force links and redirects to open in the WebView instead of in a browser
                modelView.setWebViewClient(new WebViewClient());
            }
        };
        return v;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        ivKauen = view.findViewById(R.id.iv_kauen);
        ivKauen.setOnClickListener(onClickListener);
        ivQuerschnitt = view.findViewById(R.id.iv_querschnitt);
        ivQuerschnitt.setOnClickListener(onClickListener);
    }
}
