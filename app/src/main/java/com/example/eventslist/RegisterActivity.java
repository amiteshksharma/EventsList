package com.example.eventslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailId, password, name;
    private User user;
    private Button signup;
    private TextView textViewSignIn;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUpView();
        signUpButton();
        LoginClick();
    }

    private void setUpView() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailRegister);
        password = findViewById(R.id.passwordRegister);
        signup = findViewById(R.id.registerButton);
        textViewSignIn = findViewById(R.id.textView);
        name = findViewById(R.id.usernameRegister);
        user = new User();
    }

    private void LoginClick() {
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpButton() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String p = password.getText().toString();
                String username = name.getText().toString();

                user.setId(username);
                user.setP(p);
                user.setU(email);

                if (email.isEmpty() || p.isEmpty() || username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, p).addOnCompleteListener(RegisterActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Sign Up Unsuccessful", Toast.LENGTH_SHORT).show();
                                    } else {

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Users");

                                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        myRef.child(currentUser).setValue(user);
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }
}
