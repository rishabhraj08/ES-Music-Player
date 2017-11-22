package com.example.hh.hardiks;

/**
 * Created by hh on 9/27/2017.
 */

public class Playlist
{
    String id;
    String name;
    String data;
    String count;

    public Playlist(String id, String name, String data,String count)
    {
        this.id=id;
        this.data=data;
        this.name=name;
        this.count=count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getCount(){
        return count;
    }
}
