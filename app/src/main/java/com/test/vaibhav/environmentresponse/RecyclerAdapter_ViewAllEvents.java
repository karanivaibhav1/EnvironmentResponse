package com.test.vaibhav.environmentresponse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Jesica on 4/26/2016.
 */
public class RecyclerAdapter_ViewAllEvents extends FirebaseRecyclerAdapter<User_ReportedIssues,RecyclerAdapter_ViewAllEvents.AllEventsViewHolder> {


    private Context mContext;
    private static final Firebase ref= new Firebase("https://environmentresponse.firebaseio.com/Issues");
    private String type;
    private boolean flag;

    public RecyclerAdapter_ViewAllEvents(Class<User_ReportedIssues> modelClass, int modelLayout,
                                                   Class<AllEventsViewHolder> holder, Query ref, Context context){
        super(modelClass, modelLayout, holder, ref);
        this.mContext=context;
    }

    @Override
    protected void populateViewHolder(AllEventsViewHolder aeViewHolder, User_ReportedIssues issues, int i){
        //Log.d("fb recycle adapter", "populating holder" + issues.getDescription() + "desc");
        //aeViewHolder.vTitle.setText(notifications.getName());
        aeViewHolder.vDesc.setText(issues.getDescription());
        aeViewHolder.vImage.setImageBitmap(stringToBitMap(issues.getImage()));
    }

    public class AllEventsViewHolder extends RecyclerView.ViewHolder{
        public TextView vDesc;
        public ImageView vImage;
        public AllEventsViewHolder(View v){
            super(v);
            vDesc=(TextView) v.findViewById(R.id.event_desc_recycler);
            vImage= (ImageView) v.findViewById(R.id.event_image_recycler);
        }
        public void bindEventData(String type){
            vDesc.setText(type);

        }
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
    @Override
    public RecyclerAdapter_ViewAllEvents.AllEventsViewHolder onCreateViewHolder (ViewGroup parent, int viewType){

        AllEventsViewHolder vh;
        View v= LayoutInflater.from(mContext)
                .inflate(R.layout.frament_view_all_events_recycleradapter,parent,false);
        vh=new AllEventsViewHolder(v);
        return vh;
    }

}
