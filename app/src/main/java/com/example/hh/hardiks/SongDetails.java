package com.example.hh.hardiks;

/**
 * Created by hh on 9/27/2017.
 */

public class SongDetails
{
    String id;
    String name;
    String artistName;
    String albumName;
    String path;
    String duration;


    public SongDetails(String id,String name,String artistName,String albumName,String path,String duration)
    {
        this.id = id;
        this.name=name;
        this.artistName=artistName;
        this.albumName = albumName;
        this.path=path;
        this.duration=duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getPath() {
        return path;
    }

    public String getDuration() {
        return duration;
    }
}
