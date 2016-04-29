package com.test.vaibhav.environmentresponse;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public class Activity_CreateEvent extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{
        Toolbar toolbar;
        NavigationView navigation_view;
        DrawerLayout drawer_layout;
        ActionBarDrawerToggle action_bar_drawer_toggle;
        final Firebase ref = new Firebase("https://environmentresponse.firebaseio.com/Events");
        LocationManager mLocationManager;
        LocationRequest mLocationRequest;
        GoogleApiClient mGoogleApiClient;
        Location mCurrentLocation;
        private static final long INTERVAL = 1000 * 10;
        private static final long FASTEST_INTERVAL = 1000 * 5;
        String Latitude = "NaN", Longitude = "NaN";
        private DatePicker datePicker;
        private Calendar calendar;
        private TextView dateView;
        private int year, month, day;
        private User_Events user_event = new User_Events();

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.notification_dropdown, menu);
                return true;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_createevent);
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                navigation_view = (NavigationView) findViewById(R.id.navigation_view);
                navigation_view.setNavigationItemSelectedListener(this);
                drawer_layout = (DrawerLayout) findViewById(R.id.drawer);
                action_bar_drawer_toggle = new ActionBarDrawerToggle(this, drawer_layout,
                        toolbar,
                        R.string.drawer_open,
                        R.string.drawer_close) {
                        @Override
                        public void onDrawerClosed(View drawerView) {
                                super.onDrawerClosed(drawerView);
                        }

                        @Override
                        public void onDrawerOpened(View drawerView) {
                                super.onDrawerOpened(drawerView);
                                TextView name = (TextView) findViewById(R.id.name);
                                name.setText(UserContext.getDisplayName());
                                TextView email = (TextView) findViewById(R.id.email);
                                email.setText(UserContext.getEmail());
                                ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
                                MyDownloadImageAsyncTask task = new MyDownloadImageAsyncTask(profileImage);
                                task.execute(UserContext.getProfileImageURL());
                        }
                };
                drawer_layout.setDrawerListener(action_bar_drawer_toggle);
                action_bar_drawer_toggle.syncState();

                if (!isGoogleAvailable()) {
                        return;
                }
                Log.d("Location", "onCreate");
                createLocationRequest();
                buildGoogleApiClient();
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                dateView = (TextView) findViewById(R.id.selected_date);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                Button date = (Button) findViewById(R.id.set_date);
                date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                setDate(v);
                        }
                });

                Button create = (Button) findViewById(R.id.create_event);
                create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                CheckBox check_air = (CheckBox) findViewById(R.id.check_air);
                                if (check_air.isChecked()) {
                                        user_event.setTypeAir(1);
                                } else {
                                        user_event.setTypeAir(0);
                                }
                                check_air.setChecked(false);
                                CheckBox check_water = (CheckBox) findViewById(R.id.check_water);
                                if (check_water.isChecked()) {
                                        user_event.setTypeWater(1);
                                } else {
                                        user_event.setTypeWater(0);
                                }
                                check_water.setChecked(false);
                                CheckBox check_plant = (CheckBox) findViewById(R.id.check_plant);
                                if (check_plant.isChecked()) {
                                        user_event.setTypePlant(1);
                                } else {
                                        user_event.setTypePlant(0);
                                }
                                check_plant.setChecked(false);
                                CheckBox check_trash = (CheckBox) findViewById(R.id.check_trash);
                                if (check_trash.isChecked()) {
                                        user_event.setTypeTrash(1);
                                } else {
                                        user_event.setTypeTrash(0);
                                }
                                check_trash.setChecked(false);
                                CheckBox check_soil = (CheckBox) findViewById(R.id.check_soil);
                                if (check_soil.isChecked()) {
                                        user_event.setTypeSoil(1);
                                } else {
                                        user_event.setTypeSoil(0);
                                }
                                check_soil.setChecked(false);
                                CheckBox check_other = (CheckBox) findViewById(R.id.check_other);
                                if (check_other.isChecked()) {
                                        user_event.setTypeOther(1);
                                } else {
                                        user_event.setTypeOther(0);
                                }
                                check_other.setChecked(false);
                                EditText editText = (EditText) findViewById(R.id.event_desc);
                                String desc = editText.getText().toString();
                                user_event.setDescription(desc);

                                editText.setText("");
                                editText = (EditText) findViewById (R.id.event_title);
                                String eventtitle = editText.getText().toString();
                                user_event.setTitle(eventtitle);
                                editText.setText("");

                                TextView selected_date = (TextView)findViewById(R.id.selected_date);
                                user_event.setDate(selected_date.getText().toString());

                                String reporter = UserContext.getDisplayName();
                                user_event.setReporter(reporter);

                                Log.d("Location", "latitude onCreateView" + Latitude);
                                Log.d("Location", "longitude onCreateView" + Longitude);
                                user_event.setLocationLat(Latitude);
                                user_event.setLocationLng(Longitude);

                                ref.push().setValue(user_event);
                                //Toast.makeText(getParent(), "user_event Created", Toast.LENGTH_SHORT).show();
                                TextView title = (TextView) findViewById(R.id.createEventPageTitle);
                                title.setText("Create another event?");

                        }
                });


        }
        @SuppressWarnings("deprecation")
        public void setDate(View view) {
                showDialog(555);
                Toast.makeText(this, "ca", Toast.LENGTH_SHORT)
                        .show();
        }
        @Override
        protected Dialog onCreateDialog(int id) {
                // TODO Auto-generated method stub
                if (id == 555) {
                        return new DatePickerDialog(this, myDateListener, year, month, day);
                }
                return super.onCreateDialog(id);
        }

        private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                        // TODO Auto-generated method stub
                         //arg1 = year;
                         //arg2 = month;
                         //arg3 = day;
                        showDate(year, month+1, day);
                }
        };

        private void showDate(int year, int month, int day) {
                dateView.setText(new StringBuilder().append(day).append("/")
                        .append(month).append("/").append(year));
        }

        private class MyDownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
                private final WeakReference<ImageView> imageViewReference;
                public MyDownloadImageAsyncTask(ImageView imv){
                        imageViewReference = new WeakReference<ImageView>(imv);
                }

                @Override
                protected Bitmap doInBackground(String... urls){
                        Bitmap bitmap = null;
                        for(String url : urls){
                                bitmap = UserContext.downloadImageusingHTTPGetRequest(url);
                                if(bitmap!=null){
                                        //   mImgMemoryCache.put(url,bitmap);
                                }
                        }
                        return bitmap;
                }

                //sets the bitmap returned by doInBackground
                @Override
                protected void onPostExecute(Bitmap bitmap){
                        if(imageViewReference != null && bitmap != null ){
                                final ImageView imageView = imageViewReference.get();
                                if (imageView != null){
                                        imageView.setImageBitmap(bitmap);
                                }
                        }
                }
        }
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                        case R.id.goto_events:

                                intent = new Intent(this, MapsActivity.class);
                                startActivity(intent);
                                break;
                        case R.id.legal:

                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.map, Fragment_Legal.newInstance(R.id.fragment_legal))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        case R.id.issue:
                                intent = new Intent(this, Activity_ReportIssue.class);
                                startActivity(intent);
                                break;
                        case R.id.settings:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.map, Fragment_Settings.newInstance(R.id.fragment_settings))
                                        .addToBackStack(null)
                                        .commit();
                                break;
                        default:
                                break;
                }
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
        }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
                int id = item.getItemId();
                switch(id){
                        case R.id.action_notification_dropdown:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.drawer,RecyclerView_Fragment_Notifications.newInstance(R.id.cardList))
                                        .addToBackStack(null)
                                        .commit();
                                Log.d("click on notificaitons", "gaikwadkaraniapps");
                                return true;
                        default:
                                Log.d("click on notificaitons","default");
                                return false;
                }
        }
        protected synchronized void buildGoogleApiClient() {
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
                mGoogleApiClient.connect();
                Log.d("Location", "mGoogleApiClient created");
        }

        protected void createLocationRequest() {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(INTERVAL);
                mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                Log.d("Location","mLocationRequest created");
        }
        private boolean isGoogleAvailable() {
                int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                if (ConnectionResult.SUCCESS == status) {
                        Log.d("Location", "google status is success");
                        return true;
                } else {
                        Log.d("Location", "google status is failure");
                        GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
                        return false;
                }
        }
        protected void startLocationUpdates() {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Log.d("Location", "startLocationUpdate permission denied");
                        return;

                }
                Log.d("Location", "startLocationUpdate permission received");
                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);

        }
        @Override
        public void onStart() {
                super.onStart();
                mGoogleApiClient.connect();
        }

        @Override
        public void onStop() {
                super.onStop();
                mGoogleApiClient.disconnect();
        }

        @Override
        public void onConnected(Bundle bundle) {
                startLocationUpdates();
        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onLocationChanged(Location location) {
                mCurrentLocation = location;

                Latitude = String.valueOf(mCurrentLocation.getLatitude());
                Longitude = String.valueOf(mCurrentLocation.getLongitude());
                Log.d("Testing","latitude onLocation"+ Latitude);
                Log.d("Testing","longitude onLocation"+Longitude);
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {

        }

}
