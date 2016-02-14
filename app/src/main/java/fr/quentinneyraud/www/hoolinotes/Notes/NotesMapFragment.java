package fr.quentinneyraud.www.hoolinotes.Notes;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import fr.quentinneyraud.www.hoolinotes.BuildConfig;
import fr.quentinneyraud.www.hoolinotes.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesMapFragment extends Fragment {

    private static final String TAG = "NOTES MAP FRAGMENT ===";
    private MapView mapView;

    public NotesMapFragment() {
        // Required empty public constructor
    }

    public void setLocation(Location location){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_map, container, false);

        mapView = (MapView) view.findViewById(R.id.notes_map_mapbox);

        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);

        mapView.setStyleUrl(Style.MAPBOX_STREETS);

        mapView.setLatLng(new LatLng(46.6756,4.3727));
        mapView.setZoom(15);


        mapView.onCreate(savedInstanceState);

        return view;
    }

}
