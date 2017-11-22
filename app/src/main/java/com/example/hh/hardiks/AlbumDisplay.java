package com.example.hh.hardiks;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AlbumDisplay extends Fragment {
    RecyclerView rec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_display_albumtype, container, false);

        rec = (RecyclerView) rootView.findViewById(R.id.rec1);

        rec.setAdapter(new AlbumAdapter(getActivity(), MainActivity.arr));

        rec.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        //rec.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        rec.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL));

        rec.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "position::" + position, Toast.LENGTH_SHORT).show();

                Intent ii = new Intent(getContext(), DisplayAlbumSongs.class);
                ii.putExtra("value", MainActivity.arr.get(position).getAlbumName());
                startActivity(ii);
            }
        }));

        return rootView;
    }
}
