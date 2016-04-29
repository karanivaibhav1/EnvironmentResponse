package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

/**
 * Created by Jesica on 4/19/2016.
 */
public class RecyclerView_Fragment_Notifications extends Fragment {
    private RecyclerView mRecyclerView;
    private MyFirebaseRecyclerAdapter_Notifications mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Bundle mBundle = new Bundle();
    final Firebase ref = new Firebase("https://environmentresponse.firebaseio.com/Notifications");
    public RecyclerView_Fragment_Notifications(){

    }
    public static RecyclerView_Fragment_Notifications newInstance(int selectionNumber){
        RecyclerView_Fragment_Notifications recyclerView_fragment_notifications = new RecyclerView_Fragment_Notifications();
        Bundle args = new Bundle();
        recyclerView_fragment_notifications.setArguments(args);
        return recyclerView_fragment_notifications;
    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstnceState){
        final View rootView = inflater.inflate(R.layout.notifications,container,false);
        mRecyclerView=(RecyclerView) rootView.findViewById(R.id.cardList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter=new MyFirebaseRecyclerAdapter_Notifications(User_Notifications.class,R.layout.recycler_layout, MyFirebaseRecyclerAdapter_Notifications.User_NotificationsViewHolder.class,ref,getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);


        return rootView;
    }
}
