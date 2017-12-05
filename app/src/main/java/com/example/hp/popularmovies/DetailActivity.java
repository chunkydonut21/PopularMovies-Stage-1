package com.example.hp.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        TextView title = (TextView) findViewById(R.id.title);
        TextView releaseDate = (TextView) findViewById(R.id.releaseDate);
        TextView voteAverage = (TextView) findViewById(R.id.vote_average);
        TextView language = (TextView) findViewById(R.id.language);
        TextView overview = (TextView) findViewById(R.id.overview);
        ImageView imageView = (ImageView) findViewById(R.id.picture);

        title.setText(getIntent().getExtras().getString("Title"));
        releaseDate.setText(getIntent().getExtras().getString("Release"));
        voteAverage.setText(getIntent().getExtras().getString("Vote"));
        language.setText(getIntent().getExtras().getString("Language"));
        overview.setText(getIntent().getExtras().getString("Overview"));
        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("Path")).into(imageView);


        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

}
