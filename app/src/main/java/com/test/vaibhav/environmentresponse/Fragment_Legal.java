package com.test.vaibhav.environmentresponse;

import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        final OnTextClickedListener mListener;
        try {

            mListener=(OnTextClickedListener) getContext();
        }
        catch(ClassCastException e) {
            throw new ClassCastException("This is an exception");
        }

        TextView about_this_app = (TextView) rootView.findViewById(R.id.about_this_app);
        about_this_app.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mListener.onTextClicked(R.id.about_this_app);
            }
        });
        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public interface OnTextClickedListener{
        public void onTextClicked(int i);
    }
}