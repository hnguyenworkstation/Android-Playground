package com.example.android.sunshine.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
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
    }
}
