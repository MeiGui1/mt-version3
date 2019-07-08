package com.example.frontend.Activities;

import android.content.Intent;
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
import com.example.frontend.Fragments.RecognizerFragment;
import com.example.frontend.Fragments.TranslatorFragment;
import com.example.frontend.Globals;
import com.example.frontend.Models.Patient;
import com.example.frontend.R;

public class MenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvUsername;
    private Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patient = (Patient)extras.getSerializable("patient");
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ModelsFragment()).commit();
                        break;
                    case R.id.nav_pen:
                        setTitle(getString(R.string.notes));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
                        break;
                    case R.id.nav_collections:
                        setTitle(getString(R.string.collections));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CollectionsFragment()).commit();
                        break;
                    case R.id.nav_psychosocial:
                        setTitle(getString(R.string.psychosocial));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PsychosocialFragment()).commit();
                        break;
                    case R.id.nav_recognizer:
                        setTitle(getString(R.string.recognizer));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecognizerFragment()).commit();
                        break;
                    case R.id.nav_translator:
                        setTitle(getString(R.string.translator));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TranslatorFragment()).commit();
                        break;
                    case R.id.nav_pain:
                        setTitle(getString(R.string.paindetails));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PainFragment()).commit();
                        break;
                    case R.id.nav_diagnosis:
                        setTitle(getString(R.string.diagnosis));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiagnosisFragment()).commit();
                        break;
                    case R.id.nav_drugs:
                        setTitle(getString(R.string.drugs));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DrugsFragment()).commit();
                        break;
                    case R.id.nav_exercises:
                        setTitle(getString(R.string.exercises));
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExercisesFragment()).commit();
                        break;
                    case R.id.nav_person:
                        setTitle("Patienten");
                        Intent intent = new Intent(MenuActivity.this, PatientSelectionActivity.class);
                        startActivity(intent);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openClickedFragment(View view) {
        switch(view.getId()){
            case R.id.btnModel:
                setTitle(getString(R.string.models));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ModelsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_models);
                break;
            case R.id.btnNotes:
                setTitle(getString(R.string.notes));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_pen);
                break;
            case R.id.btnCollections:
                setTitle(getString(R.string.collections));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CollectionsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_collections);
                break;
            case R.id.btnPsychosocial:
                setTitle(getString(R.string.psychosocial));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PsychosocialFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_psychosocial);
                break;
            case R.id.btnRecognizer:
                setTitle(getString(R.string.recognizer));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecognizerFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_recognizer);
                break;
            case R.id.btnTranslator:
                setTitle(getString(R.string.translator));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TranslatorFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_translator);
                break;
            case R.id.btnPain:
                setTitle(getString(R.string.paindetails));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PainFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_pain);
                break;
            case R.id.btnDiagnosis:
                setTitle(getString(R.string.diagnosis));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiagnosisFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_diagnosis);
                break;
            case R.id.btnDrugs:
                setTitle(getString(R.string.drugs));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DrugsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_drugs);
                break;
            case R.id.btnExercises:
                setTitle(getString(R.string.exercises));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExercisesFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_exercises);
                break;
        }

    }


}
