package com.example.hh.hardiks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hh on 9/28/2017.
 */

public class AlbumSongsAdapter extends RecyclerView.Adapter<AlbumSongsAdapter.MyViewHolder>
{
    Context con;
    ArrayList<SongDetails>arry;
    public AlbumSongsAdapter(Context con,ArrayList<SongDetails>arry)
    {
        this.con=con;
        this.arry=arry;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public ImageView img;
        public MyViewHolder(View view)
        {
            super(view);
            name = (TextView) view.findViewById(R.id.songName);
            img = (ImageView) view.findViewById(R.id.songImage);
        }
    }

    @Override
    public AlbumSongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);

        return new AlbumSongsAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(AlbumSongsAdapter.MyViewHolder holder, int position)
    {
        SongDetails son = arry.get(position);
        holder.name.setText(son.getName());
        String s1 = son.getPath();


        //Bitmap bb= BitmapFactory.decodeFile(s1);
        //holder.img.setImageBitmap(bb);

        try {

            byte[] raw;
            Bitmap art;
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            mmr.setDataSource(con, Uri.parse(s1));
            raw = mmr.getEmbeddedPicture();
            if (raw != null) {
                art = BitmapFactory.decodeByteArray(raw, 0, raw.length, bfo);
                holder.img.setImageBitmap(art);
            } else
                holder.img.setImageResource(R.drawable.music);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arry.size();
    }
}
