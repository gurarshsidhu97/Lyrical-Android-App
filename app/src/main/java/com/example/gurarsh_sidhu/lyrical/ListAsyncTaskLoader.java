package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gurarsh_sidhu on 4/13/2018.
 */

public class ListAsyncTaskLoader extends AsyncTaskLoader<ArrayList<song>> {



    public static String demo="http://api.musixmatch.com/ws/1.1/track.search?q_track=afwah&pagesize=3&page=1&s_track_rating=desc&apikey=";
    String id=null;
    public static String track=null;
    public static  ArrayList<song> ganna=new ArrayList<>();


    public ListAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<song> loadInBackground() {

        track=MainActivity.getTrack_name();
        demo="http://api.musixmatch.com/ws/1.1/track.search?q_track="+track+"&pagesize=3&page=1&s_track_rating=desc&apikey=";  //Add your api serial no. here

        URL url=internet.createUrl(demo);

        Log.d("url","url bnn giya");
        String jsonResponse= null;
        try {
            jsonResponse = internet.makeHTTPConeection(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("", "response aa gya");

        /*
        AAH LINES BAAR BAAR SAME NAME ADD HON TO ROKDIYAN
        */

        if(ganna.isEmpty())
            ganna =internet.extractTrackId(jsonResponse);
        else{
            ganna.clear();
            ganna=internet.extractTrackId(jsonResponse);
        }
        return ganna;
    }
}
