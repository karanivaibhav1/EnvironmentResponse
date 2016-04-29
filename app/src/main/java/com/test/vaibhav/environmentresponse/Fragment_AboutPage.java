package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jesica on 4/29/2016.
 */
public class Fragment_AboutPage extends Fragment {
    private static final String ARG_SELECTION_NUMBER = "selection_number";

    public Fragment_AboutPage() {

    }

    public static Fragment_AboutPage newInstance(int selection_number) {
        Fragment_AboutPage ffp = new Fragment_AboutPage();
        Bundle args = new Bundle();
        args.putInt(ARG_SELECTION_NUMBER, selection_number);
        ffp.setArguments(args);
        return ffp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup mainContainer, Bundle savedInstanceState) {
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_about_this_app, mainContainer, false);
        return rootView;
    }
}
