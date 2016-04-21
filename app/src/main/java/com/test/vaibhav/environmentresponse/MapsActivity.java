package com.test.vaibhav.environmentresponse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener
{

    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 10;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Fragment currFragment;
    Toolbar toolbar;
    NavigationView navigation_view;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle action_bar_drawer_toggle;
    Toolbar balloonBar;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //buildGoogleApiClient();
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.notification_dropdown, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (initMap() ) {
            Log.d("inside onCreate","value is "+(mMap!=null));
            Toast.makeText(this, "Ready to Map", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Map not available",Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        checkLocationPermission();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        balloonBar=(Toolbar) findViewById(R.id.balloon_bar);
        balloonBar.inflateMenu(R.menu.balloonbar);
        setUpToolBarItemSelected(savedInstanceState);
        balloonBar.setVisibility(View.VISIBLE);
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
    public void setUpToolBarItemSelected(final Bundle bundle) {
        balloonBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.loon_trash:
                        return true;
                    case R.id.loon_air:
                        return true;
                    case R.id.loon_soil:
                        return true;
                    case R.id.loon_plant:
                        return true;
                    case R.id.loon_water:
                        return true;
                    case R.id.loon_other:
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng suCollege = new LatLng(43.037556, -76.132639);
        mMap.addMarker(new MarkerOptions().position(suCollege).title("Life Sciences Bulding"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(suCollege, DEFAULT_ZOOM));
    }
    private boolean initMap()
    {
        if(mMap==null)
        {
            SupportMapFragment mapFrag=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            mMap=mapFrag.getMap();
        }
        Log.d("inside init map", "value is " + (mMap != null));
        checkLocationPermission();
        return (mMap!=null);
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        MapStateManager map_mgr=new MapStateManager(this);
        map_mgr.savedMapState(mMap);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        MapStateManager map_mgr=new MapStateManager(this);
        CameraPosition position=map_mgr.getSavedCameraPosition();
        if(position!=null)
        {
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
            mMap.moveCamera(update);
        }
        checkLocationPermission();
        if(balloonBar!=null)
            balloonBar.setVisibility(View.VISIBLE);

    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();
        switch(id)
        {
            case R.id.issue:
                balloonBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,Fragment_Issues.newInstance(R.id.fragment_issues))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.legal:
                balloonBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,Fragment_Legal.newInstance(R.id.fragment_legal))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.events:
                balloonBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,Fragment_Events.newInstance(R.id.fragment_events))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.settings:
                balloonBar.setVisibility(View.GONE);
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
                balloonBar.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,RecyclerView_Fragment_Notifications.newInstance(R.id.cardList))
                        .addToBackStack(null)
                        .commit();
                Log.d("click on notificaitons","test");
                return true;
            default:
                Log.d("click on notificaitons","default");
                return false;
        }
    }
}
