package com.test.vaibhav.environmentresponse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class Activity_AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_this_app);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, Fragment_AboutPage.newInstance(R.id.fragment_aboutpage))
                .addToBackStack(null)
                .commit();

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("About This App");
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);
        collapsingToolbar.setExpandedTitleColor(Color.BLACK);
        ImageView backDrop=(ImageView)findViewById(R.id.backdrop);
        backDrop.setImageResource(R.drawable.headerimage);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"SubjectHere");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Sharevia"));
            }
        });

    }

}
