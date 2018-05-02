package com.example.gurarsh_sidhu.lyrical;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class saved_lyrics extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static long song_id=0;
    SongCursorAdapter cursorAdapter;
    public static Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_lyrics);
        setTitle("Lyrics saved for...");


        getSupportLoaderManager().initLoader(3,null,this).forceLoad();


        ListView listView=(ListView) findViewById(R.id.saved_list_item);
        cursorAdapter=new SongCursorAdapter(this,null);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(saved_lyrics.this, LyricsActivity2.class);

                uri=SongDbHelper.CONTENT_URI;
                uri= ContentUris.withAppendedId(uri,id);
                song_id=id;
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection= {
                SongDbHelper.ID,
                SongDbHelper.COLUMN_Song_NAME,
                SongDbHelper.COLUMN_Artist,
                SongDbHelper.Lyrics_Path};

        return new android.support.v4.content.CursorLoader(this,SongDbHelper.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
