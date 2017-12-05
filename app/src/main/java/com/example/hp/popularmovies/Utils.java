package com.example.hp.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.hp.popularmovies.MainActivity.LOG_TAG;

public class Utils {

    private static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url = new URL(stringUrl);
        return url;
    }

    private static String httpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String response = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);// milliseconds
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving info", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Movies> extractingJson(String jsonString) throws JSONException {
        List<Movies> movies = new ArrayList<>();
        String title, overview, path, language, date, vote, imgPath;

        JSONObject root = new JSONObject(jsonString);
        JSONArray resultArray = root.getJSONArray("results");
        for (int i = 0; i < resultArray.length(); i++) {
            if (resultArray.getJSONObject(i).has("title")) {
                title = resultArray.getJSONObject(i).getString("title");
            } else {
                title = "";
            }
            overview = resultArray.getJSONObject(i).getString("overview");
            path = resultArray.getJSONObject(i).getString("poster_path");
            imgPath = "https://image.tmdb.org/t/p/w500/" + path;
            language = resultArray.getJSONObject(i).getString("original_language");
            date = resultArray.getJSONObject(i).getString("release_date");
            vote = resultArray.getJSONObject(i).getString("vote_average");

            movies.add(new Movies(title, overview, vote, language, imgPath, date));
        }

        return movies;
    }

    public static List<Movies> fetchData(String stringUrl) throws JSONException, IOException {

        URL url = createUrl(stringUrl);
        String jsonResponse = httpRequest(url);
        List<Movies> listOfMovies = extractingJson(jsonResponse);

        return listOfMovies;

    }
}
