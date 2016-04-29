package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by Jesica on 4/26/2016.
 */
public class Fragment_IssuesList extends Fragment {

    private static final String ARG_TYPE = "event_type";
    private String type;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter_ViewAllIssues mRecyclerViewAdapter;
    final Firebase ref = new Firebase("https://environmentresponse.firebaseio.com/Issues");
    private Query qref;
    public static Fragment_IssuesList newInstance(String type){
        Fragment_IssuesList fragment = new Fragment_IssuesList();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }
    public Fragment_IssuesList(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            type = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState){
        View rootView = null;
        rootView = inflater.inflate(R.layout.fragment_view_all_issues_recyclerview, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.eventlist_cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager= new LinearLayoutManager(getActivity());
        setHasOptionsMenu(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
/*
        qref= ref.getRef().equalTo(type);
        qref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Log.d("qref value:  ", qref.toString());
  */

        qref = ref.orderByChild(type).equalTo(1);
        //qref = qref.orderByChild(type).equalTo(0);

        mRecyclerViewAdapter = new RecyclerAdapter_ViewAllIssues(User_ReportedIssues.class,R.layout.frament_view_all_issues_recycleradapter,RecyclerAdapter_ViewAllIssues.AllEventsViewHolder.class,qref,getActivity());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        return rootView;
    }
}