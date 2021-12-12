package com.example.asra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.splashscreen.SplashScreen;

import com.example.asra.SQL.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatActivity activity = LoginActivity.this;
    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private Button ButtonLogin;
    private Button ButtonLoginRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    private void initListeners() {
        ButtonLogin.setOnClickListener(this);
        ButtonLoginRegister.setOnClickListener(this);
    }

    private void initViews() {
        EditTextEmail = (EditText) findViewById(R.id.email);
        EditTextPassword = (EditText) findViewById(R.id.password);
        ButtonLogin = (Button) findViewById(R.id.login);
        ButtonLoginRegister = (Button) findViewById(R.id.register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                verifyFromSQLite();
                break;
            case R.id.register:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(EditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(EditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (databaseHelper.checkUser(EditTextEmail.getText().toString().trim(), EditTextPassword.getText().toString().trim())) {
            Intent accountsIntent = new Intent(getApplicationContext(), ListActivity.class);
            accountsIntent.putExtra("EMAIL", EditTextEmail.getText().toString().trim());
            emptyInputEditText();
            Toast.makeText(activity, getString(R.string.success_return), Toast.LENGTH_LONG).show();
            startActivity(accountsIntent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_message_email), Toast.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        EditTextEmail.setText(null);
        EditTextPassword.setText(null);
    }
}