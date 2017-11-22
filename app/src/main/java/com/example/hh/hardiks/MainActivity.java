package com.example.hh.hardiks;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
    static ArrayList<SongDetails>arr;
    static ArrayList<Playlist>arr2;
    public ViewPager viewPager;
    public TabsPagerAdapter mAdapter;
    public static FirebaseStorage store;
    public android.app.ActionBar actionBar;
    int rks=-9999;

    public String[] tabs = new String[]{"Songs", "Albums","Playlists"};
    static  String key="myPreference";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSongData();
        //getPlaylistData();
        Intent ii=getIntent();
        rks=ii.getIntExtra("main",-1);
        store=FirebaseStorage.getInstance();



        viewPager = (ViewPager) findViewById(R.id.viewPager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //Adding tabs

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));

        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public void getSongData()
    {
        arr=new ArrayList<>();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String s1= MediaStore.Audio.Media.IS_MUSIC+" !=0";

        String[]projection=new String[]
                {
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                };
        Cursor cur = this.managedQuery(musicUri, projection,s1,null, null);

        cur.moveToFirst();

        while (cur.moveToNext()) {
            SongDetails song = new SongDetails(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
            arr.add(song);
        }
        Toast.makeText(this, "size is::::" + arr.size(), Toast.LENGTH_SHORT).show();
    }

   /* public void getPlaylistData()
    {
        arr2=new ArrayList<>();
        Uri musicUri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        String[]projection=new String[]
                {
                        MediaStore.Audio.Playlists._ID,
                        MediaStore.Audio.Playlists.DATA,
                        MediaStore.Audio.Playlists.NAME,
                        MediaStore.Audio.Playlists._COUNT

                };

        Cursor cur=this.managedQuery(musicUri,projection,null,null,null);
        cur.moveToFirst();
        while(cur.moveToNext())
        {
            Playlist play=new Playlist(cur.getString(0),cur.getString(1),cur.getString(2),cur.getString(3));
            arr2.add(play);
        }

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.share_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==R.id.item1)
            Toast.makeText(this,"!!Settings clicked!!",Toast.LENGTH_SHORT).show();
        else if(item.getItemId()==R.id.item2)
            Toast.makeText(this,"!!Cloud Backup!!",Toast.LENGTH_SHORT).show();

        else if(item.getItemId()==R.id.signOut)
        {
           /* final SharedPreferences sharedPreferences=getSharedPreferences(key,MODE_PRIVATE);

            SharedPreferences.Editor edit=sharedPreferences.edit();

            edit.putString("LoginId","");

            edit.apply();*/

            if(PlayMusic.mp==null)
            {
                SignInActivity.auth.signOut();
            }
            else
            {
                PlayMusic.mp.stop();
                PlayMusic.mp=null;
            }
            startActivity(new Intent(MainActivity.this,SignInActivity.class));

            Toast.makeText(MainActivity.this,"signed out successfully",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
