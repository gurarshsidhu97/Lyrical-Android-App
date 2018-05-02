package com.example.gurarsh_sidhu.lyrical;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.R.attr.id;
import static com.example.gurarsh_sidhu.lyrical.saved_lyrics.song_id;
import static com.example.gurarsh_sidhu.lyrical.saved_lyrics.uri;


public class LyricsActivity2 extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    String path=" ";
    String result=" Unable to show lyrics ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);


        uri=getIntent().getData();
        path=uri.toString();
        getSupportLoaderManager().initLoader(4,null,this).forceLoad();

        Button button=(Button) findViewById(R.id.save_button);
        button.setVisibility(View.GONE);

    }
    private String readFromFile(String path) {

        String ret=" ";
        try {
            FileInputStream fis = new FileInputStream (new File(path));
           // InputStream inputStream = openFileInput(path);

            if ( fis != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                fis.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection= {
                SongDbHelper.ID,
                SongDbHelper.Lyrics_Path};

        return new android.support.v4.content.CursorLoader(this,uri,projection,null,null,SongDbHelper.ID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       if (data.moveToFirst())
       {

           int lyrics = data.getColumnIndex(SongDbHelper.Lyrics_Path);
           String lyrics_path = data.getString(lyrics);

           result = readFromFile(lyrics_path);

           TextView textView = (TextView) findViewById(R.id.lyrics);
           textView.setText(result);

           TextView textView1= (TextView) findViewById(R.id.track_id);
           textView1.setText("id: "+ song_id);
       }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
