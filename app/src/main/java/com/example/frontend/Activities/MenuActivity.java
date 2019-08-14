package com.example.frontend.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.Fragments.CollectionsFragment;
import com.example.frontend.Fragments.DiagnosisFragment;
import com.example.frontend.Fragments.DrugsFragment;
import com.example.frontend.Fragments.ExercisesFragment;
import com.example.frontend.Fragments.ModelsFragment;
import com.example.frontend.Fragments.NotesFragment;
import com.example.frontend.Fragments.OverviewFragment;
import com.example.frontend.Fragments.PainFragment;
import com.example.frontend.Fragments.PsychosocialFragment;
import com.example.frontend.Fragments.TranslatorFragment;
import com.example.frontend.Globals;
import com.example.frontend.Models.Patient;
import com.example.frontend.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class MenuActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvUsername;
    private Patient patient;
    private Bundle bundlePatientId = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setTitle(getString(R.string.overview));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patient = (Patient) extras.getSerializable("patient");
            Globals g = Globals.getInstance();
            g.setPatient(patient);
            bundlePatientId.putInt("patientId", patient.getId());
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        drawerLayout = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_overview);
        }

        ViewTreeObserver vto = findViewById(R.id.fragment_container).getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                findViewById(R.id.fragment_container).getViewTreeObserver().removeOnPreDrawListener(this);
                Globals g = Globals.getInstance();
                g.setFragmentHeight(findViewById(R.id.fragment_container).getMeasuredHeight());
                g.setFragmentWidth(findViewById(R.id.fragment_container).getMeasuredWidth());
                return true;
            }
        });

        tvUsername = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername);
        tvUsername.setText(patient.getShortname());


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_overview:
                        setTitle(getString(R.string.overview));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();
                        break;
                    case R.id.nav_models:
                        setTitle(getString(R.string.models));
                        ModelsFragment modelsFrag = new ModelsFragment();
                        modelsFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, modelsFrag).commit();
                        break;
                    case R.id.nav_pen:
                        setTitle(getString(R.string.notes));
                        NotesFragment notesFrag = new NotesFragment();
                        notesFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFrag).commit();
                        break;
                    case R.id.nav_collections:
                        setTitle(getString(R.string.collections));
                        CollectionsFragment collectionsFrag = new CollectionsFragment();
                        collectionsFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, collectionsFrag).commit();
                        break;
                    case R.id.nav_psychosocial:
                        setTitle(getString(R.string.psychosocial));
                        PsychosocialFragment psychosocialFrag = new PsychosocialFragment();
                        psychosocialFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, psychosocialFrag).commit();
                        break;
                        /*
                    case R.id.nav_recognizer:
                        setTitle(getString(R.string.recognizer));
                        RecognizerFragment recognizerFrag = new RecognizerFragment();
                        recognizerFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recognizerFrag).commit();
                        break;
                        */
                    case R.id.nav_translator:
                        setTitle(getString(R.string.translator));
                        TranslatorFragment translatorFrag = new TranslatorFragment();
                        translatorFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, translatorFrag).commit();
                        break;
                    case R.id.nav_pain:
                        setTitle(getString(R.string.paindetails));
                        PainFragment painFrag = new PainFragment();
                        painFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, painFrag).commit();
                        break;
                    case R.id.nav_diagnosis:
                        setTitle(getString(R.string.diagnosis));
                        DiagnosisFragment diagnosisFrag = new DiagnosisFragment();
                        diagnosisFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, diagnosisFrag).commit();
                        break;
                    case R.id.nav_drugs:
                        setTitle(getString(R.string.drugs));
                        DrugsFragment drugsFrag = new DrugsFragment();
                        drugsFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, drugsFrag).commit();
                        break;
                    case R.id.nav_exercises:
                        setTitle(getString(R.string.exercises));
                        ExercisesFragment exercisesFrag = new ExercisesFragment();
                        exercisesFrag.setArguments(bundlePatientId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exercisesFrag).commit();
                        break;
                    case R.id.nav_pdf:
                        /*
                        setTitle("Patienten");
                        Intent intent = new Intent(MenuActivity.this, PatientSelectionActivity.class);
                        startActivity(intent);*/
                        //handle runtime permission for devices with marshmallow and above
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                            //system OS >= Marshmallow (6.0), check if permission is enabled or not
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                                //permission was not granted, request it
                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            }
                            else{
                                //permission already granted
                                savePdf();
                            }
                        }else{
                            //system OS < Marshmallow no required to check runtime permission
                        }
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void savePdf() {
        Document mDoc = new Document();
        //pdf file name
        String fileName = "Summary - " + patient.getShortname();
        //pdf file path
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" +fileName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(filePath));
            //open the document for writing
            mDoc.open();
            mDoc.addAuthor("Alice Truong");
            //add paragraph to the document
            mDoc.add(new Paragraph("Das ist ein Beispiel."));
            mDoc.addTitle("Titel");
            mDoc.addCreationDate();
            String imFile = "C:/itextExamples/logo.jpg";


            Drawable d = getResources().getDrawable(R.drawable.teeth); // the drawable (Captain Obvious, to the rescue!!!)
            Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();

            Image image = Image.getInstance(bitmapdata);
            mDoc.add(image);

            //close the document
            mDoc.close();
            Toast.makeText(this,fileName + ".pdf " +R.string.saved, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted from popup
                    savePdf();
                }else{
                    //permission was denied from popup, show error message
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0)
                getFragmentManager().popBackStack();
            else
                super.onBackPressed();
        }
    }

    public void openClickedFragment(View view) {
        switch (view.getId()) {
            case R.id.btnModel:
                setTitle(getString(R.string.models));
                ModelsFragment modelsFrag = new ModelsFragment();
                modelsFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, modelsFrag).commit();
                navigationView.setCheckedItem(R.id.nav_models);
                break;
            case R.id.btnNotes:
                setTitle(getString(R.string.notes));
                NotesFragment notesFrag = new NotesFragment();
                notesFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFrag).commit();
                navigationView.setCheckedItem(R.id.nav_pen);
                break;
            case R.id.btnCollections:
                setTitle(getString(R.string.collections));
                CollectionsFragment collectionsFrag = new CollectionsFragment();
                collectionsFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, collectionsFrag).commit();
                navigationView.setCheckedItem(R.id.nav_collections);
                break;
            case R.id.btnPsychosocial:
                setTitle(getString(R.string.psychosocial));
                PsychosocialFragment psychosocialFrag = new PsychosocialFragment();
                psychosocialFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, psychosocialFrag).commit();
                navigationView.setCheckedItem(R.id.nav_psychosocial);
                break;
                /*
            case R.id.btnRecognizer:
                setTitle(getString(R.string.recognizer));
                RecognizerFragment recognizerFrag = new RecognizerFragment();
                recognizerFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recognizerFrag).commit();
                navigationView.setCheckedItem(R.id.nav_recognizer);
                break;
                */
            case R.id.btnTranslator:
                setTitle(getString(R.string.translator));
                TranslatorFragment translatorFrag = new TranslatorFragment();
                translatorFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, translatorFrag).commit();
                navigationView.setCheckedItem(R.id.nav_translator);
                break;
            case R.id.btnPain:
                setTitle(getString(R.string.paindetails));
                PainFragment painFrag = new PainFragment();
                painFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, painFrag).commit();
                navigationView.setCheckedItem(R.id.nav_pain);
                break;
            case R.id.btnDiagnosis:
                setTitle(getString(R.string.diagnosis));
                DiagnosisFragment diagnosisFrag = new DiagnosisFragment();
                diagnosisFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, diagnosisFrag).commit();
                navigationView.setCheckedItem(R.id.nav_diagnosis);
                break;
            case R.id.btnDrugs:
                setTitle(getString(R.string.drugs));
                DrugsFragment drugsFrag = new DrugsFragment();
                drugsFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, drugsFrag).commit();
                navigationView.setCheckedItem(R.id.nav_drugs);
                break;
            case R.id.btnExercises:
                setTitle(getString(R.string.exercises));
                ExercisesFragment exercisesFrag = new ExercisesFragment();
                exercisesFrag.setArguments(bundlePatientId);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exercisesFrag).commit();
                navigationView.setCheckedItem(R.id.nav_exercises);
                break;
        }

    }


    public void navigateBackToPatients(View view) {
        setTitle("Patienten");
        Intent intent = new Intent(MenuActivity.this, PatientSelectionActivity.class);
        startActivity(intent);
    }
}
