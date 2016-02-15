package fr.quentinneyraud.www.hoolinotes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import fr.quentinneyraud.www.hoolinotes.Notes.NotesListFragment;
import fr.quentinneyraud.www.hoolinotes.Notes.NotesMapFragment;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;
import fr.quentinneyraud.www.hoolinotes.User.User;

public class NotesActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener, ViewPager.OnPageChangeListener{

    private static final String TAG = "====== MAIN ACTIVITY";
    private static final int PERMISSION_LOCATION = 204;

    // UI
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
        R.drawable.common_plus_signin_btn_text_light_pressed,
        R.drawable.common_plus_signin_btn_text_light_pressed
    };

    // Geolocation
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private static final int GEOLOCATION_INTERVAL = 200;
    private static final int GEOLOCATION_FAST_INTERVAL = 100;

    // Fragments
    private NotesListFragment notesListFragment;
    private NotesMapFragment notesMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.containsKey("USER_ID")){
            SessionManager.setUser(this, savedInstanceState.getString("USER_ID"));
        }

        setContentView(R.layout.activity_notes);

        // View Pager
        viewPager = (ViewPager) findViewById(R.id.notes_activity_view_pager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        // Tabs
        tabLayout = (TabLayout) findViewById(R.id.notes_activity_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();

        // Location
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, R.string.please_accept_location, Toast.LENGTH_LONG).show();
            }
        }
    }

    /*
        Geolocation
     */

    protected void onStart(){
        super.onStart();
        googleApiClient.connect();
    }

    protected void onStop(){
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // Save user uid
        outState.putString("USER_ID", SessionManager.getUser().getuId());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(GEOLOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(GEOLOCATION_FAST_INTERVAL);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, R.string.please_accept_location, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
        } else {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        sendLocationToCurrentFragment();
    }

    private void sendLocationToCurrentFragment(){
        if(currentLocation != null){
            if(viewPager.getCurrentItem() == 0){
                notesListFragment.setLocation(currentLocation);
            }else if(viewPager.getCurrentItem() == 1){
                notesMapFragment.setLocation(currentLocation);
            }
        }
    }

    /*
        UI : Tabs & Viewpager
     */

    private void setTabIcons(){
        for (int i = 0; i < tabIcons.length; i++){
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Create fragments
        notesListFragment = new NotesListFragment();
        notesMapFragment = new NotesMapFragment();

        // Add fragments to adapter & set adapter
        adapter.addFragment(notesListFragment, "LIST");
        adapter.addFragment(notesMapFragment, "MAPS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // No title, only icons
            return null;
        }
    }

    /*
        UX : Tabs
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        // Do not wait new location when tab changed
        sendLocationToCurrentFragment();
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
