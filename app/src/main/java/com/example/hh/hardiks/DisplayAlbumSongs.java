package com.example.hh.hardiks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayAlbumSongs extends AppCompatActivity
{
    RecyclerView recy;
    int len;
    ArrayList<SongDetails>arry;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_album_songs);


        recy=(RecyclerView)findViewById(R.id.recyclerViewOfAlbumSongs);

        Intent i=getIntent();
        String pos=i.getStringExtra("value");

         arry=new ArrayList<>();

         len=MainActivity.arr.size();
        //finding all songs in album touched
        for(int j=0;j<len;++j)
        {
            if(MainActivity.arr.get(j).albumName.equalsIgnoreCase(pos))
                arry.add(MainActivity.arr.get(j));
        }

        recy.setAdapter(new AlbumSongsAdapter(this,arry));
        recy.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recy.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));

        //on song touched
        recy.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(getApplicationContext(),"position::"+position,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),PlayMusic.class);
                int ii;
                for(ii=0;ii<len;ii++)
                {
                    if(MainActivity.arr.get(ii).getName().equalsIgnoreCase(arry.get(position).getName()))
                    {
                        break;
                    }
                }
                i.putExtra("value",ii);
                startActivity(i);
            }
        }));



    }
}
