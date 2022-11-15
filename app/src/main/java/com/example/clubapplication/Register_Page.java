package com.example.clubapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Dictionary;
import java.util.Hashtable;

public class Register_Page extends AppCompatActivity {

    private Button register;
    private EditText fullnameTextView;
    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText confirmPasswordTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.button3);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    public void registerUser() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fullnameTextView = findViewById(R.id.editTextTextPersonName);
        emailTextView = findViewById(R.id.editTextTextEmailAddress2);
        passwordTextView = findViewById(R.id.editTextTextPassword2);
        confirmPasswordTextView = findViewById(R.id.editTextTextPassword3);

        String email, password, fullname, confirmPassword;
        fullname = fullnameTextView.getText().toString();
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        confirmPassword = confirmPasswordTextView.getText().toString();


        // validations for input email and password

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter confirm password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter full name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (!TextUtils.equals(confirmPassword, password)) {
            Toast.makeText(getApplicationContext(),
                    "Confirm Password not Same!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!!", Toast.LENGTH_LONG).show();

                            FirebaseDatabase database = FirebaseDatabase.getInstance("https://club-application-64693-default-rtdb.asia-southeast1.firebasedatabase.app/");// This is deleted to avoid misuse
                            FirebaseUser user =  mAuth.getCurrentUser();
                            String userId = user.getUid();

                            User user1 = new User(fullname, email);

                            DatabaseReference mRef =  database.getReference().child("Users").child(userId);
                            mRef.child("email").setValue(user1.email);
                            mRef.child("fullname").setValue(user1.fullname);

                            Intent intent = new Intent(Register_Page.this, Login_Page.class);
                            startActivity(intent);
                            finish();
                        }

                        else {

                            // registration failed
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Log.e("LoginActivity", "Failed Registration", e);
                            Toast.makeText(getApplicationContext(), "Registration failed!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}