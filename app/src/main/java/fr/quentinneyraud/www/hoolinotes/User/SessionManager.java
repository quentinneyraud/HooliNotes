package fr.quentinneyraud.www.hoolinotes.User;

/**
 * Created by quentin on 08/02/16.
 */
public class SessionManager {

    private static User user = null;

    public static User getUser(){
        return user;
    }

    public static void setUser(String uId){
        user = new User(uId);
    }

}
