package fr.quentinneyraud.www.hoolinotes;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.views.MapView;

import java.text.SimpleDateFormat;

import fr.quentinneyraud.www.hoolinotes.Notes.Note;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;

public class NotesDetailActivity extends AppCompatActivity {

    private Note note;
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView textTextView;
    private MapView mapView;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);

        Bundle bundle = getIntent().getExtras();

        // Get note
        if(bundle.getString("NoteId") != null){
            note = SessionManager.getNoteById(bundle.getString("NoteId"));
        }else{
            throw new Resources.NotFoundException("Note id not defined in Detail view");
        }

        // Fill fields
        titleTextView = (TextView) findViewById(R.id.notes_detail_activity_title);
        dateTextView = (TextView) findViewById(R.id.notes_detail_activity_date);
        textTextView = (TextView) findViewById(R.id.notes_detail_activity_text);

        // Map view
        mapView = (MapView) findViewById(R.id.notes_detail_map_mapbox);
        mapView.setAccessToken(BuildConfig.MAPBOX_ACCESS_TOKEN);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapView.setZoom(15);
        mapView.setAllGesturesEnabled(false);

        InsertDatas();

        // Return listener
        returnButton = (ImageButton) findViewById(R.id.notes_detail_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView.onCreate(savedInstanceState);

    }

    private void InsertDatas(){
        mapView.setLatLng(note.getLatLng());
        titleTextView.setText(note.getTitle());
        dateTextView.setText(note.getFormattedDate());
        textTextView.setText(note.getText());
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
}
