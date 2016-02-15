package fr.quentinneyraud.www.hoolinotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import fr.quentinneyraud.www.hoolinotes.User.SessionManager;

public class NotesAddActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton returnButton;
    private EditText titleEditText;
    private EditText textEditText;
    private ActionProcessButton submitButton;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_add);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            latitude = extras.getDouble("LATITUDE");
            longitude = extras.getDouble("LONGITUDE");
        }

        // UI
        titleEditText = (EditText) findViewById(R.id.notes_add_title);
        textEditText = (EditText) findViewById(R.id.notes_add_text);
        submitButton = (ActionProcessButton) findViewById(R.id.notes_add_submit_button);

        // Listeners
        returnButton = (ImageButton) findViewById(R.id.notes_add_return);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submitButton.setOnClickListener(this);
    }

    public static void newInstance(){
        new NotesAddActivity();
    }

    @Override
    public void onClick(View v) {
        submitButton.setProgress(1);

        final String title = titleEditText.getText().toString();
        final String text = textEditText.getText().toString();

        HashMap<String, Object> note = new HashMap<String, Object>();

        note.put("createdAt", new Date());
        note.put("latitude", latitude);
        note.put("longitude", longitude);
        note.put("text", text);
        note.put("title", title);

        SessionManager.AddNote(note);

    }
}
