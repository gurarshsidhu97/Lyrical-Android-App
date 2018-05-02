package com.example.gurarsh_sidhu.lyrical;

/**
 * Created by gurarsh_sidhu on 4/1/2018.
 */

public class song {
    public  long id;
    public  int has_lyrics;
    public  String title,artist;
    song(long id1,int has_lyrics1,String title1,String artist1)
    {
        id=id1;
        has_lyrics=has_lyrics1;
        title=title1;
        artist=artist1;
    }

    public  long getid(){return id;}
    public  int getHas_lyrics(){return has_lyrics;}
    public  String getTitle(){return title;}
    public  String getArtist(){return artist;}
}
