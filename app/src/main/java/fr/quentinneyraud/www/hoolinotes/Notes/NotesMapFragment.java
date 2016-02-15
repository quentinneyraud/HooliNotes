package fr.quentinneyraud.www.hoolinotes.Notes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.Map;

import fr.quentinneyraud.www.hoolinotes.BuildConfig;
import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesMapFragment extends Fragment implements MapView.OnMarkerClickListener {

    private LatLng currentPosition;
    private MapView mapView;
    private boolean firstPositionReceived = true;
    private Map<Long, Marker> markerList;
    private static final String TAG = "NOTES MAP FRAGMENT ===";

    public NotesMapFragment() {
        // Required empty public constructor
    }

    public void setLocation(Location location){
        setCurrentPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        if(firstPositionReceived){
            goTo(getCurrentPosition());
            firstPositionReceived = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_map, container, false);

        // Map view
        mapView = (MapView) view.findViewById(R.id.notes_map_mapbox);
        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setZoom(15);

        // Move to current location or Annecy =)
        if(getCurrentPosition() != null){
            goTo(getCurrentPosition());
        }else{
            goTo(new LatLng(45.899271, 6.129500));
        }

        mapView.setOnMarkerClickListener(this);

        mapView.onCreate(savedInstanceState);

        ListenUserNotes();


        return view;
    }

    private void goTo(LatLng position){
        mapView.setLatLng(position, true);
    }

    private void ListenUserNotes(){
        SessionManager.getUser().ListenNotes(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Note note = dataSnapshot.getValue(Note.class);
                note.setId(dataSnapshot.getKey());

                Log.d(TAG, note.toString());

                mapView.addMarker(new MarkerOptions()
                        .position(note.getLatLng())
                        .title(note.getTitle())
                        .snippet(note.getText()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.getId();
        return false;
    }

    public LatLng getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(LatLng currentPosition) {
        this.currentPosition = currentPosition;
    }
}
