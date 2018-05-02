package com.example.gurarsh_sidhu.lyrical;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static android.R.attr.track;
import static android.R.attr.y;
import static android.R.id.input;

/**
 * Created by gurarsh_sidhu on 4/1/2018.
 */

public class internet {

    public static long track_id=0;
    public static String lyrics_body=null,title=null,artist=null;
    public static int has_lyrics=0;

    public static ArrayList<song> ganna=new ArrayList<song>();

    public static URL createUrl(String demo)
    {
        URL url=null;
        if(demo==null)
            return null;
        try {
            url=new URL(demo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHTTPConeection(URL url) throws IOException {
        String jsonResponse=null;
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;


        try {
            Log.d(" ","Establishing Connection...");
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            Log.d("Requesting...","Fetching details");

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            Log.d(" ","aa gya veere response");

            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();

                Log.d(" ","input Stream ch");

                jsonResponse=readFromStream(inputStream);
                Log.d(" "," string formed");
            }
            else
            {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();

        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader input = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader((input));

                String line = buffer.readLine();
                while (line != null){
                    output.append(line);
                    line = buffer.readLine();
            }
     }
        return output.toString();
    }

    public static ArrayList<song> extractTrackId(String jsonresponse)
    {
        try {
            JSONObject jsonObject=new JSONObject(jsonresponse);
            JSONObject message=jsonObject.optJSONObject("message");
            JSONObject body=message.optJSONObject("body");
            JSONArray track_list=body.optJSONArray("track_list");

            if(track_list.length()==0) {
                return ganna;
            }
            else {
                for (int i = 0; i < track_list.length(); i++) {

                    JSONObject first_one = track_list.optJSONObject(i);
                    JSONObject track = first_one.optJSONObject("track");

                    track_id = track.getLong("track_id");
                    has_lyrics = track.getInt("has_lyrics");
                    title = track.getString("track_name");
                    artist = track.getString("artist_name");

                    ganna.add(new song(track_id, has_lyrics, title, artist));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ganna;
    }

    public static String extractLyrics(String jsonresponse)
    {
        try {
            JSONObject jsonObject=new JSONObject(jsonresponse);
            JSONObject message=jsonObject.optJSONObject("message");
            JSONObject body=message.optJSONObject("body");
            JSONObject lyrics=body.optJSONObject("lyrics");

            lyrics_body=lyrics.getString("lyrics_body");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lyrics_body;
    }
}
