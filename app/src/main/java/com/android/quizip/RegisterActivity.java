package com.android.quizip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mPasswordRepeat;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //variable declarations for the UI
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.emailRegistration);
        mPassword = findViewById(R.id.passwordRegistration);
        mPasswordRepeat = findViewById(R.id.passwordRegistrationRepeat);
        mRegisterBtn = findViewById(R.id.registrationButton);
        mLoginBtn = findViewById(R.id.loginPageButton);
        progressBar = findViewById(R.id.progressBar);

        //firebase authenticator object
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Registration button activation
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String passwordRepeat = mPasswordRepeat.getText().toString().trim();
                final String fullName = mFullName.getText().toString();

                //checks for valid entries

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6){
                    mPassword.setError("Password must be >= 6 Characters.");
                    return;
                }

                if (!password.equals(passwordRepeat)){
                    mPassword.setError("Passwords must match.");
                    return;
                }

                //starts the progress bar
                progressBar.setVisibility(View.VISIBLE);

                //creates a new user in the database
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                           //adds the user to the database for saving profile information
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName", fullName);
                            user.put("email", email);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        //transfers user to the login page instead
        mLoginBtn.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
