package com.example.hh.hardiks;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import static com.example.hh.hardiks.MainActivity.store;

public class SongsDisplay extends android.support.v4.app.Fragment
{
    RecyclerView rec;
    ProgressBar pp;
    SongsAdapter song;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.activity_data_songs_display, container, false);

        rec=(RecyclerView)rootView.findViewById(R.id.recy);
        pp=(ProgressBar)(rootView).findViewById(R.id.contentLoader);

        display();

        return rootView;
    }

    private void display()
    {
        song=new SongsAdapter(getActivity(),MainActivity.arr);

        rec.setAdapter(song);

        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        rec.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        rec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(getContext(),"position::"+position,Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity().getApplicationContext(),PlayMusic.class);
                i.putExtra("value",position);
                startActivity(i);


            }
        }));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int i=-1;
        try{
            i=song.getPosition();
            Toast.makeText(getContext(),i+"",Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
            return super.onContextItemSelected(item);
        }
        String path=MainActivity.arr.get(i).getPath();
        if(item.getTitle().toString().equalsIgnoreCase("Delete"))
        {
            Toast.makeText(getContext(),MainActivity.arr.get(i).albumName,Toast.LENGTH_SHORT).show();

            //String path1=MainActivity.arr.get(i).getPath();

            MainActivity.arr.remove(i);

            File f=new File(path);
            System.out.println(path);
            Toast.makeText(getContext(),path,Toast.LENGTH_SHORT).show();
            if(f.delete())
                Toast.makeText(this.getContext(),"deleted",Toast.LENGTH_SHORT).show();
            display();

           /* Intent ii=new Intent(getContext(),MainActivity.class);
            startActivity(ii);
            ii.putExtra("main",i);*/
        }
        else
        {
            pp.setVisibility(View.VISIBLE);
            Uri file=Uri.fromFile(new File(path));

            StorageReference ref=MainActivity.store.getReference();
            StorageReference ref1=ref.child("Users").child(SignInActivity.s1).child(file.getLastPathSegment());

            StorageMetadata meta=new StorageMetadata.Builder().setCustomMetadata("Song Name",MainActivity.arr.get(i).getName())
                    .setCustomMetadata("AlbumName",MainActivity.arr.get(i).getAlbumName())
                    .setCustomMetadata("ArtistName",MainActivity.arr.get(i).getArtistName()).setCustomMetadata("Image",path)
                    .build();

            UploadTask uploadTask=ref1.putFile(file,meta);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                        System.out.println("failed or already exists");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                    pp.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                {
                    double prog=(100 * taskSnapshot.getBytesTransferred());
                   // Toast.makeText(getContext(),prog+"",Toast.LENGTH_SHORT).show();
                }
            });

        }
        return super.onContextItemSelected(item);
    }
}
