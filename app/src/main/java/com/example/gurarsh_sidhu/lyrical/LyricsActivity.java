package com.example.gurarsh_sidhu.lyrical;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.button;
import static android.R.attr.data;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static android.view.View.GONE;
import static com.example.gurarsh_sidhu.lyrical.MainActivity.lyrics_available;
import static com.example.gurarsh_sidhu.lyrical.MainActivity.test;
import static java.security.AccessController.getContext;

public class LyricsActivity extends AppCompatActivity {

   // File internalStorage = mContext.getDir("ReportPictures", Context.MODE_PRIVATE);
    public static LyricsActivity instance = null;
    String title="";
    String artist=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            try{
                title=bundle.getString("name");
                artist=bundle.getString("artist");
            }catch (Exception e){

            }
        }

        if(lyrics_available==0){
            TextView textView=(TextView) findViewById(R.id.lyrics);
            textView.setText("Lyrics for selected song is not available");

        }else getSupportLoaderManager().initLoader(2,null,songLoaderCallbacks).forceLoad();
    }

    private LoaderManager.LoaderCallbacks<String> songLoaderCallbacks = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new LyricsAsyncTaskLoader(getInstance());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, final String data) {

            TextView textView = (TextView) findViewById(R.id.lyrics);
            textView.setText(data);

            TextView textView1= (TextView) findViewById(R.id.track_id);
            textView1.setVisibility(View.GONE);

            Button button=(Button) findViewById(R.id.save_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String filepath=saveFile(data);
                    ContentValues contentValues=new ContentValues();
                    contentValues.put(SongDbHelper.COLUMN_Song_NAME,title);
                    contentValues.put(SongDbHelper.COLUMN_Artist,artist);
                    contentValues.put(SongDbHelper.Lyrics_Path,filepath);
                    Uri uri=getContentResolver().insert(
                            SongDbHelper.CONTENT_URI,contentValues);

                    if(uri!=null)
                        Toast.makeText(getApplicationContext(),"Lyrics Saved,",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"NOT Saved",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };
        public LyricsActivity getInstance(){
            return instance=this;
        }

        public String saveFile(String data) {
            String filename = title + ".txt";
            File file = new File(getInstance().getFilesDir(), filename);
            String filepath = file.toString();
            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);    //kmm di line
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                stream.write(data.getBytes());          //kmm di line
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();                     //kmm di line
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return filepath;
        }

}