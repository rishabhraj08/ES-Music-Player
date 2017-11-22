package com.example.hh.hardiks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompatSideChannelService;
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

public class SignInActivity extends AppCompatActivity
{

    Button login,forg,alre;
    EditText e1,e2;
    ProgressBar progressBar;
    static String s1,s2;
    FirebaseUser user;
    static FirebaseAuth auth;


    static SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_in);


        e1=(EditText)findViewById(R.id.signEmail);
        e2=(EditText)findViewById(R.id.signPassword);

        forg=(Button)findViewById(R.id.FrgtPass);
        alre=(Button)findViewById(R.id.SingnUp);
        login=(Button)findViewById(R.id.butnLogin);
        progressBar=(ProgressBar)findViewById(R.id.prog);

        forg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(),FireBaseActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                s1=e1.getText().toString().trim();
                s2=e2.getText().toString().trim();

                if(TextUtils.isEmpty(s1))
                {
                    Toast.makeText(getApplicationContext(),"Enter e-mail",Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(TextUtils.isEmpty(s2))
                {
                    Toast.makeText(getApplicationContext(),"Enter e-pass",Toast.LENGTH_SHORT).show();
                    return ;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(s1,s2).addOnCompleteListener(SignInActivity.this,new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        progressBar.setVisibility(View.GONE);

                        if(!task.isSuccessful())
                        {
                            if(e2.length()<6)
                            {
                                Toast.makeText(getApplicationContext(),"Password length is not up to the mark",Toast.LENGTH_SHORT).show();
                                return ;
                            }
                            else
                            {
                                Toast.makeText(SignInActivity.this,getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                            }
                        }

                        else
                        {
                            /* final SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.key,MODE_PRIVATE);

                            final SharedPreferences.Editor edit=sharedPreferences.edit();
                            edit.putString("LoginId",e1.getText().toString().trim());
                            edit.apply();*/
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                            finish();
                        }

                    }
                });

            }
        });


    }
}
