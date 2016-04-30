package com.test.vaibhav.environmentresponse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;


public class RecyclerAdapter_ViewAllIssues extends FirebaseRecyclerAdapter<User_ReportedIssues,RecyclerAdapter_ViewAllIssues.AllEventsViewHolder> {

    private Context mContext;
    private static final Firebase ref= new Firebase("https://environmentresponse.firebaseio.com/Issues");
    private User_ReportedIssues issues;
    OnItemClickListener mItemClickListener;

    public RecyclerAdapter_ViewAllIssues(Class<User_ReportedIssues> modelClass, int modelLayout,
                                         Class<AllEventsViewHolder> holder, Query ref, Context context){
        super(modelClass, modelLayout, holder, ref);
        this.mContext=context;
        //issues= ur_issues;
        issues= new User_ReportedIssues();
    }

    @Override
    protected void populateViewHolder(AllEventsViewHolder aeViewHolder, User_ReportedIssues issues, int i){
        this.issues=issues;
        aeViewHolder.vDesc.setText(issues.getDescription());
        aeViewHolder.vTitle.setText(issues.getTitle());
        aeViewHolder.vImage.setImageBitmap(stringToBitMap(issues.getImage()));
        aeViewHolder.vImage.setTransitionName(issues.getTitle());
        animate(aeViewHolder);
    }

    public class AllEventsViewHolder extends RecyclerView.ViewHolder{
        public TextView vDesc;
        public TextView vTitle;
        public ImageView vImage;
        public AllEventsViewHolder(View v){
            super(v);
            vDesc=(TextView) v.findViewById(R.id.event_desc_recycler);
            vTitle=(TextView) v.findViewById(R.id.event_title_recycler);
            vImage= (ImageView) v.findViewById(R.id.event_image_recycler);
            vImage.setTransitionName(issues.getTitle());
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (mItemClickListener!=null){
                        //v.setTransitionName(issues.getTitle());
                        mItemClickListener.onItemClick(v, issues);
                    }
                }
            });
        }
        public void bindEventData(User_ReportedIssues issues){
            vDesc.setText(issues.getDescription());
            vTitle.setText(issues.getTitle());
            vImage.setImageBitmap(stringToBitMap(issues.getImage()));
            vImage.setTransitionName(issues.getTitle());
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
    //@Override
    //public void onBindViewHolder(AllEventsViewHolder vh, int i)
    //{
    //    vh.bindEventData(issues);
    //}
    @Override
    public RecyclerAdapter_ViewAllIssues.AllEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){


        AllEventsViewHolder vh;
        View v= LayoutInflater.from(mContext)
                .inflate(R.layout.frament_view_all_issues_recycleradapter,parent,false);
        vh=new AllEventsViewHolder(v);
        return vh;
    }
    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    /*@Override
    public void onBindViewHolder(AllEventsViewHolder aeViewHolder, int position) {

        aeViewHolder.vDesc.setText(issues.getDescription());
        aeViewHolder.vImage.setImageBitmap(stringToBitMap(issues.getImage()));

        animate(aeViewHolder);

    }*/
    public interface OnItemClickListener{
        public void onItemClick(View view, User_ReportedIssues issue);
    }
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }
}
