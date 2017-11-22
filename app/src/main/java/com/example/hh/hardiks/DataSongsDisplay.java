package com.example.hh.hardiks;

import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

public  class DataSongsDisplay extends AppCompatActivity
{
    public ArrayList<SongDetails> arr=new ArrayList<>();

    RecyclerView rec;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_songs_display);

        rec=(RecyclerView)findViewById(R.id.recy);

        //rec.setLayoutManager(new GridLayoutManager(getApplicationContext(),GridLayoutManager.DEFAULT_SPAN_COUNT));

        rec.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rec.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        rec.setAdapter(new SongsAdapter(this,arr));


    }

}
