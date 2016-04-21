package com.test.vaibhav.environmentresponse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import  android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;


public class Fragment_Issues extends Fragment {

    User_ReportedIssues issue= new User_ReportedIssues();
    final Firebase ref = new Firebase("https://environmentresponse.firebaseio.com/Issues");
    private static final String ARG_SECTION_NUMBER = "selection_number";
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView uploadImage;
    public Fragment_Issues()
    {
    }
    public static Fragment_Issues newInstance(int selection_number)
    {
        Fragment_Issues ffp = new Fragment_Issues();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, selection_number);
        ffp.setArguments(args);
        return ffp;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup mainContainer, Bundle savedInstanceState)
    {
        final View rootView=inflater.inflate(R.layout.fragment_issues,mainContainer,false);

        Button submit = (Button) rootView.findViewById(R.id.submit_issue);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CheckBox check_air = (CheckBox)rootView.findViewById(R.id.check_air);
                if(check_air.isChecked()){
                    issue.setTypeAir(1);
                }
                else {
                    issue.setTypeAir(0);
                }
                check_air.setChecked(false);
                CheckBox check_water = (CheckBox)rootView.findViewById(R.id.check_water);
                if(check_water.isChecked()){
                    issue.setTypeWater(1);
                }
                else {
                    issue.setTypeWater(0);
                }
                check_water.setChecked(false);
                CheckBox check_plant = (CheckBox)rootView.findViewById(R.id.check_plant);
                if(check_plant.isChecked()){
                    issue.setTypePlant(1);
                }
                else {
                    issue.setTypePlant(0);
                }
                check_plant.setChecked(false);
                CheckBox check_trash = (CheckBox)rootView.findViewById(R.id.check_trash);
                if(check_trash.isChecked()){
                    issue.setTypeTrash(1);
                }
                else {
                    issue.setTypeTrash(0);
                }
                check_trash.setChecked(false);
                CheckBox check_soil = (CheckBox)rootView.findViewById(R.id.check_soil);
                if(check_soil.isChecked()){
                    issue.setTypeSoil(1);
                }
                else {
                    issue.setTypeSoil(0);
                }
                check_soil.setChecked(false);
                CheckBox check_other = (CheckBox)rootView.findViewById(R.id.check_other);
                if(check_other.isChecked()){
                    issue.setTypeOther(1);
                }
                else {
                    issue.setTypeOther(0);
                }
                check_other.setChecked(false);
                EditText editText = (EditText) rootView.findViewById(R.id.issue_desc);
                String desc = editText.getText().toString();
                issue.setDescription(desc);
                editText.setText("");
                Calendar c = Calendar.getInstance();
                int date = c.get(Calendar.DATE);
                issue.setDate("" + date);
                String reporter = UserContext.getDisplayName();
                issue.setReporter(reporter);
                ref.push().setValue(issue);
                Toast.makeText(getActivity(), "Issue Created", Toast.LENGTH_SHORT).show();
                TextView title = (TextView) rootView.findViewById(R.id.reportIssuePageTitle);
                title.setText("Report Another Issue?");
            }
        });
        uploadImage = (ImageView) rootView.findViewById(R.id.imageToUpload);
        uploadImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            if(uploadImage!=null)
                uploadImage.setImageURI(selectedImage);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}