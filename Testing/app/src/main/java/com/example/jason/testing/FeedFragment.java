package com.example.jason.testing;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chris on 3/25/16.
 */
public class FeedFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        View rootView = inflater
                .inflate(R.layout.feed_fragment, container, false);

        rootView.findViewById(R.id.fab).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Log.d("StudyWithMe", "opened form");
        MainActivity.instance.setContentView(R.layout.post_form);

        View.OnClickListener formButtons = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnCancel:
                        MainActivity.instance.setupSlider();
                        break;
                    case R.id.btnSubmit:

                        final String[] stuff = new String[3];

                        MainActivity.instance.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stuff[0] = ((TextView) MainActivity.instance.findViewById(R.id.txtTitle)).getText().toString();
                                stuff[1] = ((TextView) MainActivity.instance.findViewById(R.id.txtRoom)).getText().toString();
                                stuff[2] = ((TextView) MainActivity.instance.findViewById(R.id.txtDescription)).getText().toString();
                            }
                        });

                        MainActivity.instance.submitPost(stuff);

                        break;
                }
            }
        };

        MainActivity.instance.findViewById(R.id.btnCancel).setOnClickListener(formButtons);
        MainActivity.instance.findViewById(R.id.btnSubmit).setOnClickListener(formButtons);
    }

}
