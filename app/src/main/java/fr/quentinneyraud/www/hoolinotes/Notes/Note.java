package fr.quentinneyraud.www.hoolinotes.Notes;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.Date;

/**
 * Created by quentin on 07/02/16.
 */
@JsonIgnoreProperties({"id", "latLng"})
public class Note {

    private static final String TAG = "Note class";

    // Note properties
    private String id;
    private String title;
    private String text;
    private Date createdAt;
    private double latitude;
    private double longitude;

    public Note() {
    }

    public Note(String title, String text, Date createdAt, float latitude, float longitude) {
        this.setTitle(title);
        this.setText(text);
        this.setCreatedAt(createdAt);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", latLng=" + getLatLng() +
                '}';
    }
}
