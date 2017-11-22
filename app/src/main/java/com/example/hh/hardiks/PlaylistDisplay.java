package com.example.hh.hardiks;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlaylistDisplay extends Fragment
{
    RecyclerView rec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.activity_playlist_display, container, false);

        rec=(RecyclerView)rootView.findViewById(R.id.recy);

        rec.setAdapter(new SongsAdapter(getActivity(),MainActivity.arr));

        rec.setLayoutManager(new LinearLayoutManager(getContext(), GridLayoutManager.VERTICAL,false));

        rec.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        return rootView;
    }
}
