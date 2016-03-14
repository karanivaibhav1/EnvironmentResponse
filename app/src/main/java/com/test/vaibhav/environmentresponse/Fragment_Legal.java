package com.test.vaibhav.environmentresponse;

import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Legal extends Fragment {

    private static final String ARG_SECTION_NUMBER = "selection_number";
    public Fragment_Legal()
    {
    }
    public static Fragment_Legal newInstance(int selection_number)
    {
        Fragment_Legal ffp = new Fragment_Legal();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, selection_number);
        ffp.setArguments(args);
        return ffp;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup mainContainer, Bundle savedInstanceState)
    {
        View rootView=null;
        rootView=inflater.inflate(R.layout.fragment_legal,mainContainer,false);
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}