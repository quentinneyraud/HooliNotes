package fr.quentinneyraud.www.hoolinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

import fr.quentinneyraud.www.hoolinotes.User.SessionManager;
import fr.quentinneyraud.www.hoolinotes.Utils.SharedPreferencesManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SPLASH_SCREEN_ACTIVITY";
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferencesManager = new SharedPreferencesManager(this);

        if(sharedPreferencesManager.isUserLogged()){

            HashMap<String,String> user = sharedPreferencesManager.getUser();

            // Auth with shared preferences datas
            SessionManager.auth(this, user.get("EMAIL"), user.get("PASSWORD"), new Firebase.AuthResultHandler() {

                @Override
                public void onAuthenticated(AuthData authData) {

                    // Save user
                    SessionManager.setUser(getBaseContext(), authData.getUid());

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
            // start account activity
            Intent i = new Intent(SplashScreenActivity.this, AccountActivity.class);
            startActivity(i);
            finish();
        }



    }
}
