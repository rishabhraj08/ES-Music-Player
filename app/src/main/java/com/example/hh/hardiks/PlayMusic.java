package com.example.hh.hardiks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class PlayMusic extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,MediaPlayer.OnCompletionListener
{
    ImageView i2;
    TextView t1, t2, t3;
    SeekBar see;
    ImageButton ib2, ib3, ib4, ib5, ib6, ib7, ib8;
    Handler handler;
    public static MediaPlayer mp = null;
    Intent intent;
    int flag = 0;
    int repeat=0, shuffle=0;
    int k;

    String sonname, sonpath, dur, arti;


        public void startano(int k) {
        if (mp != null && mp.isPlaying())
            mp.pause();
        else if (mp != null)
            mp.start();
        else {
            String path = MainActivity.arr.get(k).getPath();
            String artist = MainActivity.arr.get(k).getArtistName();
            String name = MainActivity.arr.get(k).getName();
            try {
                byte[] raw;
                Bitmap art;
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                BitmapFactory.Options bfo = new BitmapFactory.Options();
                mmr.setDataSource(this, Uri.parse(path));
                raw = mmr.getEmbeddedPicture();
                if (raw != null) {
                    art = BitmapFactory.decodeByteArray(raw, 0, raw.length, bfo);
                    i2.setImageBitmap(art);
                } else {
                    i2.setImageResource(R.drawable.music);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            t1.setText(name);
            t2.setText(artist);

            String dur = MainActivity.arr.get(k).getDuration();

            int dd=(Integer.parseInt(dur))%(3600*1000);

            int hours = (int)( dd / (1000*60*60));
            int minutes = (int)(dd% (1000*60*60)) / (1000*60);
            int seconds = (int) ((dd% (1000*60*60)) % (1000*60) / 1000);

            if(hours>0)
                t3.setText(""+hours+":"+minutes+":"+seconds);
            else if(hours==0)
                t3.setText(""+minutes+":"+seconds);


            mp = new MediaPlayer();
            mp.setOnCompletionListener(this);
            try {
                mp.setDataSource(path);
                mp.prepare();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        see.setProgress(0);
                        see.setMax(mp.getDuration());
                        updateProgressBar();
                    }
                });
            } catch (IOException e) {
                Log.e("AudioRecordTest", "prepare() failed");
            }
        }
    }

    public void startPlaying(int k) {
        if (mp != null && mp.isPlaying())
            mp.pause();
        else if (mp != null)
            mp.start();
        else {
                String path = MainActivity.arr.get(k).getPath();
                String artist = MainActivity.arr.get(k).getArtistName();
                String name = MainActivity.arr.get(k).getName();
                byte[] raw;
                Bitmap art;
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                BitmapFactory.Options bfo = new BitmapFactory.Options();
                mmr.setDataSource(this, Uri.parse(path));
                raw = mmr.getEmbeddedPicture();
                if (raw != null) {
                    art = BitmapFactory.decodeByteArray(raw, 0, raw.length, bfo);
                    i2.setImageBitmap(art);
                } else {
                    i2.setImageResource(R.drawable.music);
                }
                mp = new MediaPlayer();
                mp.setOnCompletionListener(this);
                try {
                        mp.setDataSource(path);
                        mp.prepare();
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                                see.setProgress(0);
                                see.setMax(mp.getDuration());
                                updateProgressBar();
                            }
                        });
                } catch (IOException e) {
                    Log.e("AudioRecordTest", "prepare() failed");
                }
        }
    }

    void stopPlaying() {
        mp.stop();
        mp.reset();
        mp.release();
        mp = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmusic);

        if (mp != null)
        {
            mp.stop();
            mp.reset();
            mp = null;
        }

        repeat = 0;
        shuffle = 0;
        i2 = (ImageView) findViewById(R.id.artimg);
        see = (SeekBar) findViewById(R.id.seekBar);

        handler = new Handler();

        see.setOnSeekBarChangeListener(this);

        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);

        ib2 = (ImageButton) findViewById(R.id.iswitch1);//play image
        ib3 = (ImageButton) findViewById(R.id.iswitch2);//fast forward song

        ib4 = (ImageButton) findViewById(R.id.ib4);//next song
        ib5 = (ImageButton) findViewById(R.id.ib5);//backward song

        ib6 = (ImageButton) findViewById(R.id.ib3);//slow song

        ib7 = (ImageButton) findViewById(R.id.repeat);//repeat song

        ib8 = (ImageButton) findViewById(R.id.shuf);//shuffle song

        intent = getIntent();
        k = intent.getIntExtra("value", 0);
        SongDetails son = MainActivity.arr.get(k);


        String arti = son.getArtistName();
        String sonname = son.getName();
        String sonpath = son.getPath();
        String dur = son.getDuration();

        int dd=(Integer.parseInt(dur))%(3600*1000);

        int hours = (int)( dd / (1000*60*60));
        int minutes = (int)(dd% (1000*60*60)) / (1000*60);
        int seconds = (int) ((dd% (1000*60*60)) % (1000*60) / 1000);

        if(hours>0)
            t3.setText(""+hours+":"+minutes+":"+seconds);
        else if(hours==0)
            t3.setText(""+minutes+":"+seconds);

        t1.setText(sonname);
        t2.setText(arti);


        try {
            byte[] raw;
            Bitmap art;
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            BitmapFactory.Options bfo = new BitmapFactory.Options();
            mmr.setDataSource(this, Uri.parse(sonpath));
            raw = mmr.getEmbeddedPicture();
            if (raw != null) {
                art = BitmapFactory.decodeByteArray(raw, 0, raw.length, bfo);
                i2.setImageBitmap(art);
            } else {
                i2.setImageResource(R.drawable.music);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        startPlaying(k);

        ib2.setOnClickListener(new View.OnClickListener() {
            boolean playing = false;

            @Override
            public void onClick(View view)
            {
                startPlaying(k);
                if (playing)
                    ib2.setImageResource(R.drawable.pause);
                else {
                    ib2.setImageResource(R.drawable.play);
                }
                playing = !playing;
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currpos = mp.getCurrentPosition();
                if (currpos + 5000 <= mp.getDuration())
                    mp.seekTo(currpos + 5000);
                else
                    mp.seekTo(mp.getDuration());
            }
        });

        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                if (k < MainActivity.arr.size() - 1) {
                    startano(k + 1);
                    k = k + 1;
                } else {
                    startano(0);
                    k = 0;
                }
            }
        });

        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currpos = mp.getCurrentPosition();
                if (currpos - 5000 >= 0)
                    mp.seekTo(currpos - 5000);
                else
                    mp.seekTo(0);
            }
        });


        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
                if (k > 0) {
                    startano(k - 1);
                    k = k - 1;
                } else {
                    k = MainActivity.arr.size() - 1;
                    startano(k);

                }
            }
        });

        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeat = 1;
            }
        });


        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shuffle=1;
            }
        });

/*
        ItemTouchHelper.SimpleCallback simp=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT,ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                if(direction==ItemTouchHelper.LEFT)
                {
                    startano(k-1);
                }
                if(direction==ItemTouchHelper.RIGHT)
                {
                    startano(k+1);
                }
            }


        };
*/


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b)
    {
        if (mp != null &&b)
            mp.seekTo(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(UpdateSongTime);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(UpdateSongTime);
        int currpos = mp.getCurrentPosition();
        mp.seekTo(currpos);
        updateProgressBar();
    }

    public void updateProgressBar() {
        handler.postDelayed(UpdateSongTime, 1000);
    }
//Runnable ka kaam seek bar ko 1 sec se seek baar mein dikhana hai
    private Runnable UpdateSongTime = new Runnable() {
        public void run()
        {
            if (mp != null) {
                int mCurrentPosition = mp.getCurrentPosition();
                see.setProgress(mCurrentPosition);
            }
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    public void onCompletion(MediaPlayer mp)
    {
        stopPlaying();
        if(repeat==1)
        {
            repeat=0;
            startano(k);
        }
        if(shuffle==1)
        {
            shuffle=0;
            Random r = new Random();
            int i1 = r.nextInt(MainActivity.arr.size()-1 - 0) +0;
            startano(i1);
        }
        if (k < MainActivity.arr.size()-1) {
            startano(k + 1);
            k = k + 1;
        } else {
            startano(0);
            k = 0;
        }


    }


}
