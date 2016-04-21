package com.test.vaibhav.environmentresponse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Jesica on 4/19/2016.
 */
public class MyFirebaseRecyclerAdapter_Notifications extends FirebaseRecyclerAdapter<User_Notifications,MyFirebaseRecyclerAdapter_Notifications.User_NotificationsViewHolder> {
    private Context mContext;
    public MyFirebaseRecyclerAdapter_Notifications(Class<User_Notifications> modelClass, int modelLayout,
                                                   Class<User_NotificationsViewHolder> holder, Query ref, Context context){
        super(modelClass,modelLayout,holder,ref);
        this.mContext=context;
    }
    @Override
    protected void populateViewHolder(User_NotificationsViewHolder unViewHolder, User_Notifications notifications, int i){
        Log.d("fb recycle adapter","populating holder"+notifications.getName()+"name");
        Log.d("name is  ",notifications.getName());
        Log.d("desc is  ",notifications.getDescription());
        unViewHolder.vTitle.setText(notifications.getName());
        unViewHolder.vDesc.setText(notifications.getDescription());
    }
    public static class User_NotificationsViewHolder extends RecyclerView.ViewHolder {
        public TextView vTitle;
        public TextView vDesc;
        public User_NotificationsViewHolder(View v){
            super (v);
            vTitle=(TextView) v.findViewById(R.id.notification_title);
            vDesc=(TextView) v.findViewById(R.id.notification_desc);
        }
        public void bindUserNotifications(User_NotificationsViewHolder unvh, User_Notifications un, int i){
            unvh.vTitle.setText(un.getName());
            unvh.vDesc.setText(un.getDescription());
        }
    }
    @Override
    public MyFirebaseRecyclerAdapter_Notifications.User_NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent,false);
        User_NotificationsViewHolder unvh = new User_NotificationsViewHolder(v);
        return unvh;
    }
}
