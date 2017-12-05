package com.example.hp.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ADD YOUR API IN LINE 98
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private static final String moviesUrl = "https://api.themoviedb.org/3/movie/";
    private MoviesAdapter moviesAdapter;
    private TextView emptyView;


    public static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView lv = (GridView) findViewById(R.id.lv);
        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movies>());
        lv.setAdapter(moviesAdapter);


        emptyView = (TextView) findViewById(R.id.empty_view);
        lv.setEmptyView(emptyView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Movies movies = moviesAdapter.getItem(i);

                intent.putExtra("Title", movies.getTitle());
                intent.putExtra("Language", movies.getLanguage());
                intent.putExtra("Overview", movies.getOverview());
                intent.putExtra("Vote", movies.getVote());
                intent.putExtra("Release", movies.getRelease());
                intent.putExtra("Path", movies.getPath());
                startActivity(intent);

            }
        });

        //check for connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(1, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            emptyView.setText(R.string.no_connection);

        }

    }


    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String sortBy = sharedPreferences.getString(getString(R.string.key_sort_by), getString(R.string.default_value));
        String finalUrl = moviesUrl + sortBy + "?";

        Uri baseUri = Uri.parse(finalUrl);
        Uri.Builder ub = baseUri.buildUpon();
        ub.appendQueryParameter("api_key", "ADD YOUR API KEY HERE");

        return new MoviesLoader(this, ub.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        emptyView.setText(R.string.nothing_found);
        moviesAdapter.clear();

        if (movies != null && !movies.isEmpty()) {
            moviesAdapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        moviesAdapter.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);

                return true;
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(1, null, this);
    }


}

