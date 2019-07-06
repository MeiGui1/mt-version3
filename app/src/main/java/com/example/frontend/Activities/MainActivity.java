package com.example.frontend.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.frontend.R;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvAttemptsInfo;
    private Button btnLogin;
    private int counter = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvAttemptsInfo = (TextView) findViewById(R.id.tvAttempts);
        tvAttemptsInfo.setText("No of attempts remaining: " + counter);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        //jump to Menu for faster testing puroposes
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("usernameKey", "Admin");
        startActivity(intent);
    }

    private void validate(String username, String password) {
        if (username.equals("Admin") && password.equals("123")) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("usernameKey", etUsername.getText().toString());
            startActivity(intent);
        } else {
            counter--;
            tvAttemptsInfo.setText("No of attempts remaining: " + String.valueOf(counter));
            if (counter == 0) {
                btnLogin.setEnabled(false);
            }
        }

    }
}
