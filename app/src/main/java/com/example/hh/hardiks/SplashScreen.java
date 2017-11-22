package com.example.hh.hardiks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread time= new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(1000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    final SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.key,MODE_PRIVATE);

                    //final SharedPreferences.Editor edit=sharedPreferences.edit();

                    if(sharedPreferences.getString("LoginId","").equals(""))

                    {
                        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                    }
                    else
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        };
        time.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
