package fr.quentinneyraud.www.hoolinotes.User;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import fr.quentinneyraud.www.hoolinotes.R;

/**
 * Created by quentin on 08/02/16.
 */
public class User {

    private String uId;
    private Firebase userRef;
    private ChildEventListener cel;
    private ValueEventListener vel;

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
        String url = context.getResources().getString(R.string.firebase_base);
        userRef = new Firebase(url).child("users").child(uId);

        setuId(uId);
    }

    public void ListenNotes(ChildEventListener childEventListener){
        userRef.child("notes").addChildEventListener(childEventListener);
    }


}
