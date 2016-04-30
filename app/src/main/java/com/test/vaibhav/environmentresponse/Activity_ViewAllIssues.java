package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

public class Activity_ViewAllIssues extends AppCompatActivity
    implements Fragment_IssuesList.OnItemClickedListener
{
    Adapter_ViewAllIssues allEvents;
    ViewPager view_pager;
    Fragment currFragment;
    User_ReportedIssues issues;

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_view_all_issues_viewpager);

        allEvents=new Adapter_ViewAllIssues(getSupportFragmentManager(),6);
        view_pager=(ViewPager) findViewById(R.id.pager);
        view_pager.setAdapter(allEvents);
        view_pager.setCurrentItem(0);

        TabLayout tab_layout=(TabLayout) findViewById(R.id.tabs);
        tab_layout.setupWithViewPager(view_pager);


        view_pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                //final float normalised_position=Math.abs(Math.abs(position)-1);
                //page.setScaleX(normalised_position/2+0.5f);
                //page.setScaleY(normalised_position/2+0.5f);
                page.setRotationY(position * -30);
            }
        });
    }
    @Override
    public void onItemClickedListener(User_ReportedIssues issue, View v)
    {
        issues= issue;
        currFragment=Fragment_IssueDetails.newInstance(issues);
        currFragment.setSharedElementEnterTransition(new DetailsTransition().setDuration(1500));
        currFragment.setEnterTransition(new Fade().setDuration(1500));
        currFragment.setExitTransition(new Fade().setDuration(1500));
        currFragment.setSharedElementReturnTransition(new DetailsTransition().setDuration(1500));

        try {
            Log.d("transition name", issues.getTitle());
            getSupportFragmentManager().beginTransaction()
                    .addSharedElement(v, issues.getTitle())
                    .replace(R.id.activity_framelayout_eventdetails, currFragment)
                    .addToBackStack(null)
                    .commit();
        }
        catch (Exception e){
            Log.d("shared element issue","exception");
        }
    }
    public class DetailsTransition extends TransitionSet {
        public DetailsTransition() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds())
                    .addTransition(new ChangeTransform())
                    .addTransition(new ChangeImageTransform());
        }
    }
}
