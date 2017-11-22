package com.example.hh.hardiks;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayAlbumtype extends AppCompatActivity
{
    RecyclerView rec1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_albumtype);
        rec1=(RecyclerView)findViewById(R.id.rec1);

        rec1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rec1.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rec1.setAdapter(new AlbumAdapter(this,MainActivity.arr));
    }

}
