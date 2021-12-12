package com.example.asra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asra.SQL.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = RegisterActivity.this;

    private EditText EditTextName;
    private EditText EditTextEmail;
    private EditText EditTextPassword;
    private EditText EditTextConfirmPassword;
    private Button ButtonRegister;
    private Button ButtonLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    private void initListeners() {
        ButtonRegister.setOnClickListener(this);
        ButtonLogin.setOnClickListener(this);
    }

    private void initViews() {
        EditTextName = (EditText) findViewById(R.id.username);
        EditTextEmail = (EditText) findViewById(R.id.email);
        EditTextPassword = (EditText) findViewById(R.id.password);
        EditTextConfirmPassword = (EditText) findViewById(R.id.confirmpassword);

        ButtonRegister = (Button) findViewById(R.id.register);
        ButtonLogin = (Button) findViewById(R.id.login);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                postDataToSQLite();
                break;
            case R.id.login:
                finish();
                break;
        }
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(EditTextName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(EditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(EditTextPassword, EditTextConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!databaseHelper.checkUser(EditTextEmail.getText().toString().trim())) {
            user.setName(EditTextName.getText().toString().trim());
            user.setEmail(EditTextEmail.getText().toString().trim());
            user.setPassword(EditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(getApplicationContext(), getString(R.string.success_register), Toast.LENGTH_LONG).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(getApplicationContext(), getString(R.string.email_exists), Toast.LENGTH_LONG).show();

        }
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void emptyInputEditText() {
        EditTextName.setText(null);
        EditTextEmail.setText(null);
        EditTextPassword.setText(null);
        EditTextConfirmPassword.setText(null);
    }
}