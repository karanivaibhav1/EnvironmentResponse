package com.test.vaibhav.environmentresponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jesica on 4/30/2016.
 */
public class Fragment_IssueDetails extends Fragment {

    private ShareActionProvider mShareActionProvider;
    private static final String ARG_ISSUE = "issue";
    private User_ReportedIssues issue;

    public Fragment_IssueDetails(){

    }
    public static Fragment_IssueDetails newInstance(User_ReportedIssues issue)
    {
        Fragment_IssueDetails ffp = new Fragment_IssueDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ISSUE, issue);
        //args.putInt(ARG_MOVIE_POSITION,position);
        ffp.setArguments(args);
        return ffp;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            issue= (User_ReportedIssues)getArguments().getSerializable(ARG_ISSUE);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup mainContainer, Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        View rootView=null;
        rootView=inflater.inflate(R.layout.fragment_issuedetails,mainContainer,false);

        ImageView img = (ImageView) rootView.findViewById(R.id.issue_image);
        TextView title=(TextView) rootView.findViewById(R.id.issue_title);
        TextView type=(TextView) rootView.findViewById(R.id.issue_type);
        TextView reporter=(TextView) rootView.findViewById(R.id.issue_reporter);
        TextView date=(TextView) rootView.findViewById(R.id.issue_date);
        TextView location=(TextView) rootView.findViewById(R.id.location);
        TextView desc=(TextView) rootView.findViewById(R.id.issue_description);

        img.setImageBitmap(stringToBitMap(issue.getImage()));
        img.setTransitionName(issue.getTitle());
        Log.d("in fragment", (String) issue.getTitle());
        title.setText(issue.getTitle());
        reporter.setText(issue.getReporter());
        date.setText(issue.getDate());
        location.setText(issue.getLocationLat()+" , "+issue.getLocationLng());
        desc.setText(issue.getDescription());
        if(issue.getTypeAir()==1){
            type.setText("Type_Air");
        }
        else if(issue.getTypeWater()==1){
            type.setText("Type_Water");
        }
        else if(issue.getTypeTrash()==1){
            type.setText("Type_Trash");
        }
        else if(issue.getTypePlant()==1){
        type.setText("Type_Plant");
        }
        else if(issue.getTypeSoil()==1){
            type.setText("Type_Soil");
        }
        else{
            type.setText("Type_Other");
        }
        return rootView;
    }
    public Bitmap stringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}