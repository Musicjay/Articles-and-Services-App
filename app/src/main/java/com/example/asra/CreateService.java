package com.example.asra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asra.SQL.DatabaseHelper;
import com.example.asra.SQL.ServicesDatabaseHelper;

public class CreateService extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CreateService.this;

    private EditText EditTextName;
    private EditText EditTextDescription;
    private EditText EditTextPrice;
    private Button ButtonCreate;

    private InputValidation inputValidation;
    private ServicesDatabaseHelper databaseHelper;

    private Services service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        initViews();
        initListeners();
        initObjects();
    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new ServicesDatabaseHelper(activity);
        service = new Services();
    }

    private void initListeners() {
        ButtonCreate.setOnClickListener(this);
    }

    private void initViews() {
        EditTextName = (EditText) findViewById(R.id.name);
        EditTextDescription = (EditText) findViewById(R.id.description);
        EditTextPrice = (EditText) findViewById(R.id.price);

        ButtonCreate = (Button) findViewById(R.id.create_listing);
    }


    @Override
    public void onClick(View view) {
        postDataToSQLite();
    }

    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(EditTextName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextDescription, getString(R.string.error_message_description))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(EditTextPrice, getString(R.string.error_message_price))) {
            return;
        }

        service.setName(EditTextName.getText().toString().trim());
        service.setDescription(EditTextDescription.getText().toString().trim());
        service.setPrice(EditTextPrice.getText().toString().trim());
        databaseHelper.addService(service);
        Toast.makeText(getApplicationContext(), getString(R.string.success_create), Toast.LENGTH_LONG).show();
        emptyInputEditText();

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    private void emptyInputEditText() {
        EditTextName.setText(null);
        EditTextDescription.setText(null);
        EditTextPrice.setText(null);
    }
}
