package com.example.paras.blogapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login_id,create_id;
    private EditText user_id,pass_id;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireauthlistner;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_id = findViewById(R.id.login_id);
        create_id = findViewById(R.id.create_id);
        user_id = findViewById(R.id.user_id);
        pass_id = findViewById(R.id.pass_id);

        login_id.setOnClickListener(this);
        create_id.setOnClickListener(this);

        //now initializing firebase auth for login

        firebaseAuth = FirebaseAuth.getInstance();


        fireauthlistner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    Toast.makeText(MainActivity.this, "Signed in! ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                    finish();
                } else
                    Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.login_id:

                String email = user_id.getText().toString().trim();
                String password = pass_id.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).
                            addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful())
                                        Toast.makeText(MainActivity.this, "Successfully Signed in", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();

                                }
                            });

                }
                else
                    Toast.makeText(this, "Invalid fill blocks", Toast.LENGTH_SHORT).show();
                break;

            case R.id.create_id:
                startActivity(new Intent(MainActivity.this,NewAccount.class));
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(fireauthlistner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(fireauthlistner != null)
            firebaseAuth.removeAuthStateListener(fireauthlistner);
    }
}
