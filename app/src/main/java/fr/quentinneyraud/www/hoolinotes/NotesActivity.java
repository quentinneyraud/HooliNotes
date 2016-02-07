package fr.quentinneyraud.www.hoolinotes;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, LocationListener, ViewPager.OnPageChangeListener {

    private static final String TAG = "====== MAIN ACTIVITY";

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
        setContentView(R.layout.activity_notes);

        // View Pager
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);

        // Tabs
        tabLayout = (TabLayout) findViewById(R.id.main_activity_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabIcons();

        // Location
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
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
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(GEOLOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(GEOLOCATION_FAST_INTERVAL);

        // Watch user position
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        sendLocationToCurrentFragment();
    }

    private void sendLocationToCurrentFragment(){
        if(viewPager.getCurrentItem() == 0){
            notesListFragment.setLocation(currentLocation);
        }else if(viewPager.getCurrentItem() == 1){
            notesMapFragment.setLocation(currentLocation);
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
//        private final List<String> fragmentTitleList = new ArrayList<>();

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
//            fragmentTitleList.add(title);
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
