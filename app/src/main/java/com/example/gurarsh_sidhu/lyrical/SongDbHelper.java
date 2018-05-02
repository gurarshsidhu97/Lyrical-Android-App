package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by gurarsh_sidhu on 4/15/2018.
 */

public class SongDbHelper extends SQLiteOpenHelper {

    public static final String CONTENT_AUTHORITY = "com.example.gurarsh_sidhu.lyrical";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SONGS = "Saved_songs";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SONGS);

    public final static String TABLE_NAME = "Saved_list";

    public final static String ID = BaseColumns._ID;

    public final static String COLUMN_Song_NAME ="name";

    public final static String COLUMN_Artist = "artist";

    public final static String Lyrics_Path="lyrics_path";


    public SongDbHelper(Context context) {
        super(context, "lyrical.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e(" ","Table bnaun lagge");

        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_Song_NAME + " TEXT NOT NULL, "
                + COLUMN_Artist + " TEXT,"
                + Lyrics_Path +" TEXT UNIQUE);";

        String delete ="DELETE FROM "+TABLE_NAME +";";
       /* String demo="INSERT INTO " + TABLE_NAME +" ( "
                + ID + ","
                + COLUMN_Song_NAME + ","
                + COLUMN_Artist
                + ")"
                + "VALUES "
                +"(1,'Qismat','Ammy Virk');";

        String demo1="INSERT INTO " + TABLE_NAME +" ( "
                + ID + ","
                + COLUMN_Song_NAME + ","
                + COLUMN_Artist
                + ")"
                + "VALUES "
                +"(2,'Trending nakhra','Amrit Mann');";*/

        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME +";");

        db.execSQL(SQL_CREATE_PETS_TABLE);

        Log.e("","bnn gya veere database");
        db.execSQL(delete);
     /*   db.execSQL(demo);
        db.execSQL(demo1);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME +";");
        onCreate(db);
    }
}
