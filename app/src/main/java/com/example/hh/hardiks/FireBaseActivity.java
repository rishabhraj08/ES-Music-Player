package com.example.hh.hardiks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class FireBaseActivity extends AppCompatActivity
{

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    Intent ii;
    static  FirebaseAuth auth;
    static FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);

        ii=getIntent();
        auth = FirebaseAuth.getInstance();

        user=auth.getCurrentUser();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e1, e2;
                e1 = inputEmail.getText().toString().trim();
                e2 = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(e1)) {
                    Toast.makeText(getApplicationContext(), "enter e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(e2)) {
                    Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (e2.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(view.VISIBLE);

                auth.createUserWithEmailAndPassword(e1, e2).addOnCompleteListener(FireBaseActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Toast.makeText(FireBaseActivity.this, "create user" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful())
                        {
                            Toast.makeText(FireBaseActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                            {
                                user.sendEmailVerification().addOnCompleteListener(FireBaseActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(!task.isSuccessful())
                                            Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(FireBaseActivity.this, MainActivity.class));
                                finish();
                            }
                    }});

            }
        });
    }

}
