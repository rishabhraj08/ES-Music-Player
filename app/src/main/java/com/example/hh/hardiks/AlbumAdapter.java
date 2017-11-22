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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by hh on 9/27/2017.
 */

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder>
{
    Context con;
    ArrayList<SongDetails>arr1;



    public AlbumAdapter(Context con,ArrayList<SongDetails>arr1)
    {
        this.con=con;
        this.arr1=arr1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,count;
        public ImageView img1;

        public MyViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.tx1);
            count=(TextView) view.findViewById(R.id.tx2);
            img1=(ImageView)view.findViewById(R.id.img1);

        }
    }


    @Override
    public AlbumAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        SongDetails son=arr1.get(position);
        String str=son.albumName;
        int c=1;
        holder.name.setText(son.getAlbumName());
        for(int ii=0;ii<arr1.size();ii++)
        {
                SongDetails so=arr1.get(ii);
            if(ii!=position)
            {
                if(str.equalsIgnoreCase(so.getAlbumName()))
                    c++;
            }
        }

        holder.count.setText("No Of Songs => "+c);
        String s1=son.getPath();
        //Glide.with(con).load(son.getId()).into(holder.img1);
       // holder.img1.setImageResource(R.drawable.music);

        /*Bitmap bb= BitmapFactory.decodeFile(s1);
        holder.img1.setImageBitmap(bb);*/
        try {

            byte[] raw;
            Bitmap art;
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            mmr.setDataSource(con, Uri.parse(s1));
            raw = mmr.getEmbeddedPicture();
            if (raw != null) {
                art = BitmapFactory.decodeByteArray(raw, 0, raw.length, bfo);
                holder.img1.setImageBitmap(art);
            } else
                holder.img1.setImageResource(R.drawable.music);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Picasso.with(con).load(s1).centerCrop().resize(100,100).into(holder.img);
    }



    @Override
    public int getItemCount() {
        return arr1.size();
    }
}

