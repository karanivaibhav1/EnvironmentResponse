package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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

        qref = ref.orderByChild(type).equalTo(1);
        //qref = qref.orderByChild(type).equalTo(0);
        final OnItemClickedListener mListener;
        try
        {
            mListener=(OnItemClickedListener)getContext();
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException("This is an exception");
        }
        mRecyclerViewAdapter = new RecyclerAdapter_ViewAllIssues(User_ReportedIssues.class,R.layout.frament_view_all_issues_recycleradapter,RecyclerAdapter_ViewAllIssues.AllEventsViewHolder.class,qref,getActivity());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        setHasOptionsMenu(true);

        mRecyclerViewAdapter.SetOnItemClickListener(new RecyclerAdapter_ViewAllIssues.OnItemClickListener() {
            @Override
            public void onItemClick(View view, User_ReportedIssues issue) {
                mListener.onItemClickedListener(issue, view);
            }

        });
        return rootView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        if (menu.findItem(R.id.search)==null) {
            menuInflater.inflate(R.menu.search_bar, menu);
        }
        mRecyclerView=(RecyclerView) getActivity().findViewById(R.id.eventlist_cardList);
        SearchView search=(SearchView) menu.findItem(R.id.search).getActionView();
        if(search!=null)
        {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                @Override
                public boolean onQueryTextSubmit(String query){
                    int i=0;
                    //for(;i<md.getSize();i++)
                    //{
                     //   if(query.equalsIgnoreCase((String) md.getItem(i).get("name"))){
                      //      break;
                       // }
                    //}
                    //if(i>=0 && i<md.getSize()){
                        mRecyclerView.scrollToPosition(i+1);
                    //}
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query){
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, menuInflater);
    }
    public interface OnItemClickedListener
    {
        public void onItemClickedListener(User_ReportedIssues issue, View view);
    }
}

