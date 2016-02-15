package fr.quentinneyraud.www.hoolinotes.Notes;


import android.content.Context;
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
public class NotesMapFragment extends Fragment implements MapView.OnMarkerClickListener, SessionManager.NotesListener {

    private static final String TAG = "NOTES MAP FRAGMENT ===";

    // mapbox
    private MapView mapView;
    private LatLng currentPosition;
    private boolean firstPositionReceived = true;
    private Map<Long, Marker> markerList;

    private NotesMapListener notesMapListener;

    public NotesMapFragment() {
        // Required empty public constructor
    }

    public void setLocation(Location location){
        setCurrentPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        if(firstPositionReceived){
            goTo(getCurrentPosition(), false);
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
            goTo(getCurrentPosition(), false);
        }else{
            goTo(new LatLng(45.894027, 6.133081), false);
        }

        mapView.setOnMarkerClickListener(this);

        mapView.onCreate(savedInstanceState);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotesMapListener) {
            notesMapListener = (NotesMapListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NotesListListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()  {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void goTo(LatLng position, boolean animated){
        mapView.setLatLng(position, animated);
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

    @Override
    public void onNoteAdded(Note note) {
        mapView.addMarker(new MarkerOptions()
                .position(note.getLatLng())
                .title(note.getTitle())
                .snippet(note.getText().substring(0, 20) + "..."));
    }

    public interface NotesMapListener{
        void onItemSelectedOnMap(String id);
    }
}
