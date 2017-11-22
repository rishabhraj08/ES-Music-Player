package com.example.hh.hardiks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hh on 9/27/2017.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> implements View.OnCreateContextMenuListener
{
    Context con;
    ArrayList<SongDetails> arr1;
    int position;
    public void setPosition(int pos)
    {
        this.position=pos;
    }

    public int getPosition()
    {
        return position;
    }


    public SongsAdapter(Context con, ArrayList<SongDetails> arr1) {
        this.con = con;
        this.arr1 = arr1;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
    {
        /*menu.add("Delete");
        menu.add("Backup");*/

        menu.add("Backup");
        menu.add("Delete");
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.songName);
            img = (ImageView) view.findViewById(R.id.songImage);
        }
    }


    @Override
    public SongsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        itemView.setOnCreateContextMenuListener(this);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        SongDetails son = arr1.get(position);
        holder.name.setText(son.getName());
        holder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                setPosition(position);
                return false;
            }
        });
        String s1 = son.getPath();

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
            }
        catch (Exception e)
                {
                    e.printStackTrace();
                }

    }
        @Override
        public int getItemCount () {
            return arr1.size();
        }

}
