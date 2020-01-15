package com.drchip.ihelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    EditText etEmail, etPassword, etConfirmPassword;
    Button btnCancel, btnConfirm;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    ImageView ivLogo;

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
        ivLogo = findViewById(R.id.ivLogo);

        mAuth = FirebaseAuth.getInstance();
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
                        progressDialog.setTitle("Sign in");
                        progressDialog.setMessage("Connecting with server...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                        progressDialog.show();

                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    ApplicationClass.currentUser = mAuth.getCurrentUser();
                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    startActivity(new Intent(Register.this, MainActivity.class));

                                    Register.this.finish();


                                } else {

                                    Snackbar.make(findViewById(R.id.activity_register), "Please enter all fields", Snackbar.LENGTH_LONG).setAction("Error", null).show();

                                }

                            }
                        });

                    }
                }
            }
        });




    }
}
