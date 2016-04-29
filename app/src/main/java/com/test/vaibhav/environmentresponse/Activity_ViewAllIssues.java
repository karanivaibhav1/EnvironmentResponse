package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

public class Activity_ViewAllIssues extends AppCompatActivity {
    Adapter_ViewAllIssues allEvents;
    ViewPager view_pager;

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
}
