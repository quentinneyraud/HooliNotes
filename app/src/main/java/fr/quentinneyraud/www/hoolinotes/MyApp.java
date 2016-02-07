package fr.quentinneyraud.www.hoolinotes;

import com.firebase.client.Firebase;

/**
 * Created by quentin on 08/02/16.
 */
public class MyApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
