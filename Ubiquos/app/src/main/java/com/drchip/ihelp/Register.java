package com.drchip.ihelp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText etEmail, etPassword, etConfirmPassword;
    Button btnCancel, btnConfirm;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etPasswordConfirm);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()
                        || etConfirmPassword.toString().isEmpty()) {

                    Snackbar.make(v, "Please enter all fields", Snackbar.LENGTH_LONG).setAction("Error", null).show();

                } else {
                    if (etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                        String email = etEmail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                    }
                }
            }
        });




    }
}
