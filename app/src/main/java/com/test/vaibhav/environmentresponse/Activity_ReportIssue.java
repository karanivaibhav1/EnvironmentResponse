package com.test.vaibhav.environmentresponse;


import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.location.LocationManager;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;



import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_ReportIssue extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
{

    Toolbar toolbar;
    NavigationView navigation_view;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle action_bar_drawer_toggle;
    User_ReportedIssues issue= new User_ReportedIssues();
    final Firebase ref = new Firebase("https://environmentresponse.firebaseio.com/Issues");

    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView uploadImage;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    String Latitude = "NaN", Longitude = "NaN";
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int GALLERY_REQUEST_CODE = 1;
    private NotificationCompat.Builder mBuilder;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_dropdown, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportissue);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation_view=(NavigationView)findViewById(R.id.navigation_view);
        navigation_view.setNavigationItemSelectedListener(this);
        drawer_layout=(DrawerLayout)findViewById(R.id.drawer);
        action_bar_drawer_toggle=new ActionBarDrawerToggle(this,drawer_layout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView){
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
        Log.d("Location","onCreate");
        createLocationRequest();
        buildGoogleApiClient();

/*        TextView location_latlon= (TextView) findViewById(R.id.user_location);
        if(location_latlon!=null)
            location_latlon.setText(Latitude+" , "+Longitude);
*/
        mBuilder = new NotificationCompat.Builder(this);
        Button submit = (Button) findViewById(R.id.submit_issue);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CheckBox check_air = (CheckBox)findViewById(R.id.check_air);
                if(check_air.isChecked()){
                    issue.setTypeAir(1);
                }
                else {
                    issue.setTypeAir(0);
                }
                check_air.setChecked(false);
                CheckBox check_water = (CheckBox)findViewById(R.id.check_water);
                if(check_water.isChecked()){
                    issue.setTypeWater(1);
                }
                else {
                    issue.setTypeWater(0);
                }
                check_water.setChecked(false);
                CheckBox check_plant = (CheckBox)findViewById(R.id.check_plant);
                if(check_plant.isChecked()){
                    issue.setTypePlant(1);
                }
                else {
                    issue.setTypePlant(0);
                }
                check_plant.setChecked(false);
                CheckBox check_trash = (CheckBox)findViewById(R.id.check_trash);
                if(check_trash.isChecked()){
                    issue.setTypeTrash(1);
                }
                else {
                    issue.setTypeTrash(0);
                }
                check_trash.setChecked(false);
                CheckBox check_soil = (CheckBox)findViewById(R.id.check_soil);
                if(check_soil.isChecked()){
                    issue.setTypeSoil(1);
                }
                else {
                    issue.setTypeSoil(0);
                }
                check_soil.setChecked(false);
                CheckBox check_other = (CheckBox)findViewById(R.id.check_other);
                if (check_other.isChecked()) {
                    issue.setTypeOther(1);
                }
                else {
                    issue.setTypeOther(0);
                }
                check_other.setChecked(false);

                EditText editText = (EditText) findViewById(R.id.issue_desc);
                String desc = editText.getText().toString();
                issue.setDescription(desc);
                editText.setText("");

                editText = (EditText) findViewById(R.id.issue_title);
                String title = editText.getText().toString();
                issue.setTitle(title);
                editText.setText("");

                Date currDate = new Date();
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String postedDate = format.format(currDate);
                issue.setDate(postedDate);

                String reporter = UserContext.getDisplayName();
                issue.setReporter(reporter);

                Log.d("Location", "latitude onCreateView" + Latitude);
                Log.d("Location", "longitude onCreateView" + Longitude);
                issue.setLocationLat(Latitude);
                issue.setLocationLng(Longitude);

                ref.push().setValue(issue);
                Toast.makeText(getApplicationContext(), "Issue Created", Toast.LENGTH_SHORT).show();
                TextView page_title = (TextView) findViewById(R.id.reportIssuePageTitle);
                page_title.setText("Report Another Issue?");
                uploadImage.setImageBitmap(null);
                showNotifications(title);
            }
        });
        uploadImage = (ImageView) findViewById(R.id.imageToUpload);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent;
                        switch (item.getItemId()) {
                            case R.id.upload_from_gallery:
                                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, GALLERY_REQUEST_CODE);
                                return true;
                            case R.id.upload_from_camera:
                                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.upload_image_popup,popup.getMenu());
                popup.show();
            }
        });

    }
    void showNotifications(String title){

        mBuilder.setSmallIcon(R.drawable.notification_icon);
        mBuilder.setContentTitle("Environment Response Issue");
        mBuilder.setContentText("Issue " +title+" created");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
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
    public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();
        Intent intent;
        switch(id)
        {
            case R.id.goto_events:

                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.legal:

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,Fragment_Legal.newInstance(R.id.fragment_legal))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.events:
                intent = new Intent(this, Activity_CreateEvent.class);
                startActivity(intent);
                break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,Fragment_Settings.newInstance(R.id.fragment_settings))
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            if(uploadImage!=null) {
                uploadImage.setImageURI(selectedImage);
                try {
                    Bitmap bmp = ((BitmapDrawable) uploadImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                    //bmp.recycle();
                    byte[] byteArray = bYtE.toByteArray();
                    String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    issue.setImage(imageFile);
                }
                catch(Exception e){
                    Log.d("image", "image too large");
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK && data !=null){
            try {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                uploadImage.setImageBitmap(bmp);
                ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
                //bmp.recycle();
                byte[] byteArray = bYtE.toByteArray();
                String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
                issue.setImage(imageFile);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
