package com.example.android.sunshine.app;

/**
 * Created by hungnguyen on 3/23/16.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bars when an item is clicked
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Declare array adapter
        ArrayAdapter<String> mArrayAdapter;

        String[] forecastArray = {
                "Today - Raining - 56/52",
                "Tomorrow - Sunny - 67/54",
                "Weds - Snowy - 24/22",
                "Thurs - Sunny - 34/22",
                "Friday - Sunny - 45/30",
                "Sat - Heavy Rain - 50/46",
                "Sunday - Sunny - 67/58"
        };

        List<String> listForecast = new ArrayList<String>(
                Arrays.asList(forecastArray)
        );

        mArrayAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview, listForecast
        );

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mArrayAdapter);

        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
        private final String LOG_Tag = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // This string will contain the raw JSON file
            String forecastJson = null;

            try {
                // Create the URL for weather app query
                String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7";
                String apiKey = "&APPID=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;
                URL url = new URL(baseUrl.concat(apiKey));

                // create the request to Open weather map, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input string and load to the string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if(inputStream == null) {
                    // get nothing from the api server
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line=reader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }

                if (stringBuffer.length() == 0) {
                    // string is empty
                    return null;
                }

                forecastJson = stringBuffer.toString();

            } catch (IOException e) {
                Log.e("PlaceHolderFragment", "Error", e);
                return null;
            } finally {
                // if the connection is null
                // disconnect the connection
                if (urlConnection == null) {
                    urlConnection.disconnect();
                }
                if (reader == null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_Tag, "Error closing stream", e);
                    }
                }
            }

            return null;
        }
    }
}