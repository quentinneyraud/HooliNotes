package fr.quentinneyraud.www.hoolinotes;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH_SCREEN_ACTIVITY";
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        sharedPreferencesManager = new SharedPreferencesManager(this);

        if(sharedPreferencesManager.isUserLogged()){

            HashMap<String,String> user = sharedPreferencesManager.getUser();

            // Auth with shared preferences datas
            new Firebase(getResources().getString(R.string.firebase_base)).authWithPassword(user.get("EMAIL"), user.get("PASSWORD"), new Firebase.AuthResultHandler() {

                @Override
                public void onAuthenticated(AuthData authData) {

                    // Start notes activity
                    Intent i = new Intent(SplashScreenActivity.this, NotesActivity.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Toast.makeText(SplashScreenActivity.this, "Erreur auth", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            // start login activity
            Toast.makeText(SplashScreenActivity.this, "Start login activity", Toast.LENGTH_SHORT).show();
        }



    }
}
