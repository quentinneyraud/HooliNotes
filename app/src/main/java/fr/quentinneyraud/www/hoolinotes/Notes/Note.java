package fr.quentinneyraud.www.hoolinotes.Notes;

import android.util.Log;

import java.util.Date;

/**
 * Created by quentin on 07/02/16.
 */
public class Note {

    private static final String TAG = "Note class";

    // Note properties
    private String title;
    private String text;
    private Date createdAt;
    private float latitude;
    private float longitude;

    public Note() {
    }

    public Note(String title, String text, Date createdAt, float latitude, float longitude) {
        this.setTitle(title);
        this.setText(text);
        this.setCreatedAt(createdAt);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        Log.d(TAG, "New note created " + this.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Note title: " + this.getTitle() + " description: " + this.getText() + " created at: " + this.getCreatedAt() + " on lat: " + this.getLatitude() + " / long: " + this.getLongitude();
    }
}
