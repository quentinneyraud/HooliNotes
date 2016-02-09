package fr.quentinneyraud.www.hoolinotes.User;

import android.content.Context;

import com.firebase.client.Firebase;

import fr.quentinneyraud.www.hoolinotes.R;

/**
 * Created by quentin on 08/02/16.
 */
public class SessionManager {

    private static User user = null;

    public static User getUser(){
        return user;
    }

    public static void setUser(Context context, String uId){
        user = new User(context, uId);
    }

    public static void auth(Context context, String email, String password, Firebase.AuthResultHandler firebaseAuthResultHandler){
        String firebaseUrl = context.getResources().getString(R.string.firebase_base);

        new Firebase(firebaseUrl).authWithPassword(email, password, firebaseAuthResultHandler);
    }

}
