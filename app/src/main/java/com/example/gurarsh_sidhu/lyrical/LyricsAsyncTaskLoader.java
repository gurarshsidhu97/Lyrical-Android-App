package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.gurarsh_sidhu.lyrical.MainActivity.editText;
import static com.example.gurarsh_sidhu.lyrical.internet.track_id;

/**
 * Created by gurarsh_sidhu on 4/1/2018.
 */

public class LyricsAsyncTaskLoader extends AsyncTaskLoader<String> {

  //  public static String demo="http://api.musixmatch.com/ws/1.1/track.search?q_track=afwah&pagesize=3&page=1&s_track_rating=desc&apikey=";
    public static String demo1=null;
    String id=null;
    public static String lyrics=null;

    public LyricsAsyncTaskLoader(Context context) {
        super(context);
    }


    @Override
    public String loadInBackground() {

        demo1="http://api.musixmatch.com/ws/1.1/track.lyrics.get?track_id="+track_id+"&pagesize=3&page=1&s_track_rating=desc&apikey=";   //Add your api serial no here...

        id= String.valueOf(track_id );

        String jsonResponse=null;
        URL url=internet.createUrl(demo1);
        try {
            jsonResponse=internet.makeHTTPConeection(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lyrics=internet.extractLyrics(jsonResponse);
        return lyrics;
    }
}
