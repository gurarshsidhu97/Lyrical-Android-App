package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gurarsh_sidhu on 4/15/2018.
 */

public class SongCursorAdapter extends CursorAdapter {
    public SongCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView artist=(TextView) view.findViewById(R.id.artist);
        TextView title=(TextView) view.findViewById(R.id.track);

        int name=cursor.getColumnIndex(SongDbHelper.COLUMN_Song_NAME);
        int kalakar=cursor.getColumnIndex(SongDbHelper.COLUMN_Artist);


        String name1=cursor.getString(name);
        String kalakar1=cursor.getString(kalakar);

        title.setText(name1);
        artist.setText(kalakar1);
    }
}
