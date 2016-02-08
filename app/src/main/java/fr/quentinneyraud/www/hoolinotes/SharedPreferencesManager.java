package fr.quentinneyraud.www.hoolinotes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by quentin on 08/02/16.
 */
public class SharedPreferencesManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Shared preferences config
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "HooliNotesPreferences";

    // Structure config
    private static final String EMAIL_KEY = "EMAIL";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String USER_ID_KEY = "USER_ID";

    public SharedPreferencesManager(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public HashMap<String, String> getUser(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(EMAIL_KEY, preferences.getString(EMAIL_KEY, null));
        user.put(PASSWORD_KEY, preferences.getString(PASSWORD_KEY, null));

        return user;
    }

    public String getUserUid(){
        return preferences.getString(USER_ID_KEY, null);
    }

    public void setUserUid(String userUid){
        editor.putString(USER_ID_KEY, userUid);
    }

    public void LogInUser(String email, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);

        editor.apply();
    }

    public void LogoutUser(){
        editor.remove(EMAIL_KEY);
        editor.remove(PASSWORD_KEY);
        editor.putBoolean(IS_LOGIN, false);

        editor.apply();
    }

    public boolean isUserLogged(){
        return preferences.getBoolean(IS_LOGIN, false);
    }
}
