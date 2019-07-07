package com.example.frontend.Activities;

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
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.Fragments.CollectionsFragment;
import com.example.frontend.Fragments.ModelsFragment;
import com.example.frontend.Fragments.NotesFragment;
import com.example.frontend.Globals;
import com.example.frontend.R;

public class MenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tvUsername;
    private int patientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            patientId = extras.getInt("patientId");
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
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ModelsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_models);
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
        tvUsername.setText(Integer.toString(patientId));


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_collections:
                        setTitle("Medien");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CollectionsFragment()).commit();
                        break;
                    case R.id.nav_pen:
                        setTitle("Notizen");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
                        break;
                    case R.id.nav_models:
                        setTitle("3D-Modelle");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ModelsFragment()).commit();
                        break;
                    case R.id.nav_person:
                        setTitle("Benutzer");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CollectionsFragment()).commit();
                        Toast.makeText(MenuActivity.this, "I am a Person", Toast.LENGTH_SHORT).show();
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
}
