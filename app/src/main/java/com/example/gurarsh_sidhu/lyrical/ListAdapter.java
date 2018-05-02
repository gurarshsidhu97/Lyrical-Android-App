package com.example.gurarsh_sidhu.lyrical;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurarsh_sidhu on 4/1/2018.
 */

public class ListAdapter extends ArrayAdapter<song> {
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<song> objects) {
        super(context, resource, objects);


    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        song track = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            //notifyDataSetChanged();
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.single_list, parent, false);
        }

        TextView title=(TextView) listItemView.findViewById(R.id.track);
        String name=track.getTitle();
        title.setText(name);

        TextView artist=(TextView) listItemView.findViewById(R.id.artist);
        String artist_name=track.getArtist();
        artist.setText(artist_name);


        return listItemView;
    }
}
