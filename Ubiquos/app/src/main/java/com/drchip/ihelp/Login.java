package com.drchip.ihelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 2;
    private static final String TAG = "GOOGLE LOGIN";
    public GoogleSignInClient mGoogleSignInClient;
    Button btnRegister, btnLogin;
    EditText etMail, etPassword;
    SignInButton signInButton;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;// ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        etMail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);


        //mAuth = FirebaseAuth.getInstance();
        //  FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Login in");
                progressDialog.setMessage("Connecting with server...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                progressDialog.show();

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMail.getText().toString().isEmpty() || etPassword.toString().isEmpty()) {

                    Snackbar.make(v, "Please enter all fields ", Snackbar.LENGTH_SHORT).show();

                } else {
                    String email, password;
                    email = etMail.getText().toString().trim();
                    password = etPassword.getText().toString().trim();
                    progressDialog.setTitle("Login in");
                    progressDialog.setMessage("Connecting with server...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                ApplicationClass.currentUser = mAuth.getCurrentUser();
                                progressDialog.dismiss();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                Login.this.finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                //Toast.makeText(Login.this, "Authentication failed.",
                                //Toast.LENGTH_SHORT).show();
                                Snackbar.make(findViewById(R.id.activity_login), "Authentication failed. ", Snackbar.LENGTH_SHORT).show();

                                progressDialog.dismiss();

                            }

                        }
                    });
                }
            }
        });

        ApplicationClass.currentUser = mAuth.getCurrentUser();
        if (ApplicationClass.currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            Login.this.finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Error Login in", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressDialog.dismiss();
                            ApplicationClass.currentUser = mAuth.getCurrentUser();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            Login.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.activity_login), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

}
