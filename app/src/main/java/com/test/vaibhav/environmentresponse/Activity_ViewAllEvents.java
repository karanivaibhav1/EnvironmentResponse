package com.test.vaibhav.environmentresponse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class Activity_ViewAllEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> notes = new ArrayList<String>();
        notes.add("Come");
        notes.add("On");
        notes.add("Flip");
        notes.add("Me");

        //You can also use FlipViewController.VERTICAL
       // FlipViewController flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);

        //We're creating a NoteViewAdapter instance, by passing in the current context and the
        //values to display after each flip
       // flipView.setAdapter(new NoteViewAdapter(this, notes));

       // setContentView(flipView);
    }
//    - See more at: http://www.ahotbrew.com/how-to-implement-flipboard-animation-on-android-tutorial/#sthash.i8L5Kjuq.dpuf
}
