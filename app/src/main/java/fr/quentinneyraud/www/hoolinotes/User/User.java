package fr.quentinneyraud.www.hoolinotes.User;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;

import fr.quentinneyraud.www.hoolinotes.R;

/**
 * Created by quentin on 08/02/16.
 */
public class User {

    private String uId;
    private Firebase userRef;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public User(){

    }

    public User(Context context, String uId){

        // Get context & create reference to user
        String url = context.getResources().getString(R.string.firebase_base) + "/users/" + uId;
        userRef = new Firebase(url);

        setuId(uId);
    }

    public void getNotes(ChildEventListener cel){
        userRef.child("notes").addChildEventListener(cel);
    }


}
