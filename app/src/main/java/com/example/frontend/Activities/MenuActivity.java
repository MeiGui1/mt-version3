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
import com.example.frontend.Models.DiagnosisType;
import com.example.frontend.Models.DrugType;
import com.example.frontend.Models.Note;
import com.example.frontend.Models.Patient;
import com.example.frontend.Models.PatientDiagnosis;
import com.example.frontend.Models.PatientDocument;
import com.example.frontend.Models.PatientDrug;
import com.example.frontend.Models.PatientExercise;
import com.example.frontend.Models.PatientImage;
import com.example.frontend.R;
import com.example.frontend.Service.JsonPlaceHolderApi;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;


import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.itextpdf.text.html.HtmlTags.FONT;

public class MenuActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvUsername;
    private Patient patient;
    private Bundle bundlePatientId = new Bundle();
    private List<Note> allNotesOfPatient = new ArrayList<>();
    private List<DiagnosisType> allDiagnosisTypes = new ArrayList<>();
    private List<PatientDiagnosis> allPatientDiagnoses = new ArrayList<>();
    private List<DrugType> allDrugTypes = new ArrayList<>();
    private List<PatientDrug> allPatientDrugs = new ArrayList<>();
    private List<PatientExercise> allPatientExercises = new ArrayList<>();
    private List<PatientImage> allPatientImages = new ArrayList<>();
    private List<PatientDocument> allPatientDocument = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://consapp.herokuapp.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

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
                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                //permission was not granted, request it
                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                requestPermissions(permissions, STORAGE_CODE);
                            } else {
                                //permission already granted
                                createPdf();
                            }
                        } else {
                            //system OS < Marshmallow no required to check runtime permission
                        }
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void createPdf() {
        getAllDrugTypes();
        getPatientDrugs();
        getPatientDiagnoses();
    }

    private void savePdf() {
        Document mDoc = new Document();
        //pdf file name
        String fileName = "Summary - " + patient.getShortname();
        //pdf file path
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName + ".pdf";
        try {
            PdfWriter.getInstance(mDoc, new FileOutputStream(filePath));
            String timeStamp = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
            BaseColor darkBlueColor = new BaseColor(15,28,75);
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
            Font fontParagraphTitle = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, darkBlueColor);
            Font fontListTitle = new Font(Font.FontFamily.HELVETICA, 14, Font.ITALIC, BaseColor.DARK_GRAY);
            //open the document for writing
            mDoc.setMargins(70, 70, 80, 80);
            mDoc.open();
            mDoc.addAuthor("Alice Truong");
            mDoc.addTitle("Schmerzsprechstunde vom " + timeStamp);
            mDoc.addCreationDate();

            String strGreeting = "";
            //add paragraph to the document
            if (patient.getGender().equals("Female")) {
                strGreeting = "Sehr geehrte Frau " + patient.getShortname() + ",";
            } else {
                strGreeting = "Sehr geehrter Herr " + patient.getShortname() + ",";
            }
            addLineBreak(mDoc, 8);
            Chunk title = new Chunk("Schmerzsprechstunde am Zentrum für Zahnmedizin");
            Paragraph pTitle = new Paragraph(title);
            mDoc.add(pTitle);

            addLineBreak(mDoc, 2);
            Paragraph pGreeting = new Paragraph(strGreeting);
            mDoc.add(pGreeting);
            addLineBreak(mDoc, 1);
            mDoc.add(new Paragraph("gerne berichten wir über Ihre Schmerzsprechstunde vom " + timeStamp + " und geben Ihnen eine Zusammenfassung Ihrer bisherigen Befunde und des weiteren Vorgehens."));

            Drawable d = getResources().getDrawable(R.drawable.uzh_logo);
            Image uzh_logo = createImageWithDrawable(d);
            uzh_logo.scalePercent(9);
            uzh_logo.setAbsolutePosition(380, 730);
            mDoc.add(uzh_logo);

            if (!allPatientDiagnoses.isEmpty()) {
                addLineBreak(mDoc, 1);
                Chunk diagnosisTitle = new Chunk("Diagnose", fontParagraphTitle);
                Paragraph pDiagnosisTitle = new Paragraph(diagnosisTitle);
                mDoc.add(pDiagnosisTitle);
                String strFollowingDiagnosis = "folgende Diagnose";
                if (allPatientDiagnoses.size() > 1) {
                    strFollowingDiagnosis = "folgenden Diagnosen";
                }
                mDoc.add(new Paragraph("Aufgrund Ihrer Symptome und der bisherigen Befunde wurden die " + strFollowingDiagnosis + " gestellt:"));
                com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                for (PatientDiagnosis pd : allPatientDiagnoses) {
                    for (DiagnosisType dt : allDiagnosisTypes) {
                        if (pd.getDiagnosisId() == dt.getId()) {
                            ListItem listItem = new ListItem();
                            Chunk diagnosisName = new Chunk(dt.getName(), fontListTitle);
                            listItem.add(diagnosisName);
                            if (pd.getComment() != null) {
                                Chunk strDiagnosisComment = new Chunk(" (" + pd.getComment() + ")", fontListTitle);
                                listItem.add(strDiagnosisComment);
                            }
                            if (dt.getDescription() != null) {
                                Chunk strDiagnosisDesc = new Chunk("\n" + dt.getDescription());
                                listItem.add(strDiagnosisDesc);
                            }

                            list.add(listItem);

                        }
                    }
                }
                mDoc.add(list);
            }
            if (!allPatientDrugs.isEmpty()) {
                addLineBreak(mDoc, 1);
                Chunk diagnosisTitle = new Chunk("Medikamente", fontParagraphTitle);
                Paragraph pDiagnosisTitle = new Paragraph(diagnosisTitle);
                mDoc.add(pDiagnosisTitle);
                String strFollowingDrugs = "";
                if (allPatientDrugs.size() > 1) {
                    strFollowingDrugs = "folgenden Medikamenten";
                }

                com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                for (PatientDrug pd : allPatientDrugs) {
                    for (DrugType dt : allDrugTypes) {
                        if (pd.getDrugTypeId() == dt.getId()) {
                            ListItem listItem = new ListItem();
                            Chunk diagnosisName = new Chunk(dt.getName(), fontListTitle);
                            listItem.add(diagnosisName);
                            if (pd.getAmount() != null && !pd.getAmount().isEmpty()) {
                                Chunk strDiagnosisAmount = new Chunk(" (" + pd.getAmount() + ")", fontListTitle);
                                listItem.add(strDiagnosisAmount);
                            }
                            if (pd.getDosis() != null && !pd.getDosis().isEmpty()) {
                                Chunk strDiagnosisDosis = new Chunk(" (" + pd.getDosisInText() + ")", fontListTitle);
                                listItem.add(strDiagnosisDosis);
                            }
                            if (dt.getDescription() != null) {
                                Chunk strDiagnosisDesc = new Chunk("\n" + dt.getDescription());
                                listItem.add(strDiagnosisDesc);
                            }
                            list.add(listItem);
                        }
                    }
                }
                mDoc.add(new Paragraph("Wir empfehlen Ihnen die Einnahme von" + strFollowingDrugs + ":"));
                mDoc.add(list);
            }
            if (!allNotesOfPatient.isEmpty()) {
                addLineBreak(mDoc, 1);
                Chunk notesTitle = new Chunk("Notizen", fontParagraphTitle);
                Paragraph pNotesTitle = new Paragraph(notesTitle);
                mDoc.add(pNotesTitle);
                mDoc.add(new Paragraph("Folgende Notizen wurden während der Sprechstunde für Sie erstellt:"));
                for (Note note : allNotesOfPatient) {
                    Image noteImage = Image.getInstance(note.getNoteBytes());
                    noteImage.scalePercent(40);
                    noteImage.setBorder(Rectangle.BOX);
                    noteImage.setBorderColor(BaseColor.BLACK);
                    noteImage.setBorderWidth(1f);
                    mDoc.add(noteImage);
                }
            }

            //close the document
            mDoc.close();
            Toast.makeText(this, fileName + ".pdf " + getString(R.string.saved) +"!", Toast.LENGTH_SHORT).show();

        } catch (
                Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void addLineBreak(Document doc, int lines) throws DocumentException {
        for (int i = 0; i < lines; i++) {
            doc.add(new Paragraph(" "));
        }
    }

    private Image createImageWithDrawable(Drawable d) throws IOException, BadElementException {
        // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        Image image = Image.getInstance(bitmapdata);
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission was granted from popup
                    savePdf();
                } else {
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

    public void getPatientNotes() {
        Call<List<Note>> call = jsonPlaceHolderApi.getSelectedNotesOfPatient(patient.getId());
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "not succesful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allNotesOfPatient = response.body();
                    savePdf();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public void getPatientDiagnoses() {
        //Get all PatientDiagnoses of Patient
        Call<List<PatientDiagnosis>> call = jsonPlaceHolderApi.getPatientDiagnoses(patient.getId());
        call.enqueue(new Callback<List<PatientDiagnosis>>() {
            @Override
            public void onResponse(Call<List<PatientDiagnosis>> call, Response<List<PatientDiagnosis>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "GetPatientDiagnosesOfClass not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allPatientDiagnoses = response.body();
                    getAllDiagnosisTypes();
                }
            }

            @Override
            public void onFailure(Call<List<PatientDiagnosis>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllDiagnosisTypes() {
        //Get all DiagnosisTypes of Patient
        Call<List<DiagnosisType>> call = jsonPlaceHolderApi.getAllDiagnosisTypes();
        call.enqueue(new Callback<List<DiagnosisType>>() {
            @Override
            public void onResponse(Call<List<DiagnosisType>> call, Response<List<DiagnosisType>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "Get DiagnosisTypes not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allDiagnosisTypes = response.body();
                    getPatientNotes();
                }
            }

            @Override
            public void onFailure(Call<List<DiagnosisType>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPatientDrugs() {
        //Get all PatientDrugs of Patient
        Call<List<PatientDrug>> call = jsonPlaceHolderApi.getAllDrugsOfPatient(patient.getId());
        call.enqueue(new Callback<List<PatientDrug>>() {
            @Override
            public void onResponse(Call<List<PatientDrug>> call, Response<List<PatientDrug>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "GetPatientDrugsOfClass not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allPatientDrugs = response.body();

                }
            }

            @Override
            public void onFailure(Call<List<PatientDrug>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllDrugTypes() {
        //Get all DrugTypes of Patient
        Call<List<DrugType>> call = jsonPlaceHolderApi.getAllDrugTypes();
        call.enqueue(new Callback<List<DrugType>>() {
            @Override
            public void onResponse(Call<List<DrugType>> call, Response<List<DrugType>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MenuActivity.this, "Get DrugTypes not successful", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    allDrugTypes = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<DrugType>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
