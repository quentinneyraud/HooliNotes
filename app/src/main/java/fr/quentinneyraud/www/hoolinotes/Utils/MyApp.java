package fr.quentinneyraud.www.hoolinotes.Utils;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by quentin on 08/02/16.
 */
public class MyApp extends android.app.Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        // Save app context
        context = this;

        // Set up Firebase
        Firebase.setAndroidContext(this);
    }
}
