package com.example.hp.popularmovies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.hp.popularmovies.R.id.iv;


public class MoviesAdapter extends ArrayAdapter<Movies> {

    public MoviesAdapter(Context context, ArrayList<Movies> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display, parent, false);
            holder = new ViewHolder();
            holder.iv = convertView.findViewById(iv);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movies movies = getItem(position);

        String imageUrl = movies.getPath();

        Picasso.with(getContext()).load(imageUrl).
                placeholder(R.drawable.load_image).
                error(R.drawable.no_image).
                into(holder.iv);

        return convertView;
    }

}


