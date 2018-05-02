package com.example.gurarsh_sidhu.lyrical;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;

import static android.R.attr.data;
import static com.example.gurarsh_sidhu.lyrical.SongDbHelper.TABLE_NAME;

/**
 * Created by gurarsh_sidhu on 4/15/2018.
 */

public class SongProvider extends ContentProvider {

    SongDbHelper songDbHelper;

    private static final int SONGS = 100;
    private static final int SONGS_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
      sUriMatcher.addURI(SongDbHelper.CONTENT_AUTHORITY,SongDbHelper.PATH_SONGS,SONGS);
      sUriMatcher.addURI(SongDbHelper.CONTENT_AUTHORITY,SongDbHelper.PATH_SONGS + "/#",SONGS_ID);
    }

    @Override
    public boolean onCreate() {

        Log.e(" ","creating SongDbHelper object");
        songDbHelper=new SongDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Log.d(" ","query ch aa giya");

        SQLiteDatabase database = songDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case SONGS:

                cursor=database.query(TABLE_NAME,projection,null,null,null,null,sortOrder);
                break;
            case SONGS_ID:
                selection = SongDbHelper.ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs,null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase db= songDbHelper.getWritableDatabase();


        long rowID = db.insert(TABLE_NAME, "", values);

        try {
            if (rowID > 0) {
                Uri _uri = ContentUris.withAppendedId(SongDbHelper.CONTENT_URI, rowID);
                getContext().getContentResolver().notifyChange(_uri, null);
                return _uri;
            }


        }catch (SQLException e)
        {
            throw new SQLException("Failed to add a record into " + uri);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
