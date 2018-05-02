package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.button;
import static android.R.attr.id;
import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.example.gurarsh_sidhu.lyrical.internet.createUrl;
import static com.example.gurarsh_sidhu.lyrical.internet.lyrics_body;
import static com.example.gurarsh_sidhu.lyrical.internet.track_id;

public class MainActivity extends AppCompatActivity { //implements LoaderManager.LoaderCallbacks<ArrayList<song>>


    public static EditText editText;
    public static String track_name = null;
    public static int lyrics_available=0;
    public static MainActivity instance = null;
    public static ArrayList<song> test = new ArrayList<>();
    public  ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance=this;

        editText=(EditText) findViewById(R.id.search);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    /* IK KEYBOARD HIDE KRN VASTE TE DOOJA UPRLA FUNCTION SEARCH BUTTON KEYBOARD CH ADD KRN LYI
                    * JE IK TO JIADA EDIT VIEW HUNDE PHIR SHYD AAPAN CONTEXT NAA LAINDE KALLA input_method_service NAAL KMM CHLAUNDE
                    * AAH THALE AALI LINE KITE ONJ PADHI ...*/

                    InputMethodManager imm = (InputMethodManager) getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    getSupportLoaderManager().initLoader(1, null, listLoaderCallbacks).forceLoad();
                    return true;
                }
        return false;
        }
    });

        track_name= editText.getText().toString().trim();

        ImageButton imageButton=(ImageButton) findViewById(R.id.search_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportLoaderManager().initLoader(1,null,listLoaderCallbacks).forceLoad();
            }
        });

        Button button=(Button) findViewById(R.id.saved_lyrics);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,saved_lyrics.class);
                startActivity(intent);
            }
        });
    }


        private LoaderManager.LoaderCallbacks<ArrayList<song>> listLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<song>>() {
        @Override
        public Loader<ArrayList<song>> onCreateLoader(int id, Bundle args) {

            return new ListAsyncTaskLoader(getInstance());

        }

        @Override
        public void onLoadFinished(Loader<ArrayList<song>> loader, ArrayList<song> data) {

            ListView listView = (ListView) findViewById(R.id.list_item);
            TextView textView = (TextView) findViewById(R.id.no_song);
            if(data.isEmpty()){
                textView.setText("No matching song found");
                listView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            }
            else {
                textView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listAdapter = new ListAdapter(getInstance(), 0, data);
                listView.setAdapter(listAdapter);


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(MainActivity.this, LyricsActivity.class);

                        song track = (song) parent.getItemAtPosition(position);
                        track_id = track.getid();
                        lyrics_available = track.getHas_lyrics();
                        String name=track.getTitle();
                        String artist=track.getArtist();

                        if(name!=null)
                             intent.putExtra("name",name);
                        if(artist!=null)
                            intent.putExtra("artist", artist);

                        startActivity(intent);
                    }
                });
            }
         }

        @Override
        public void onLoaderReset(Loader<ArrayList<song>> loader) {

        }
    };


    public static String getTrack_name() {
        track_name = editText.getText().toString().trim();
        return track_name;
    }
    public MainActivity getInstance(){
        return instance=this;
    }

}
