package fr.quentinneyraud.www.hoolinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import fr.quentinneyraud.www.hoolinotes.Account.SignInFragment;
import fr.quentinneyraud.www.hoolinotes.Account.SignUpFragment;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;
import fr.quentinneyraud.www.hoolinotes.Utils.SharedPreferencesManager;

public class AccountActivity extends AppCompatActivity implements SignInFragment.SignInListener, SignUpFragment.SignUpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        changeFragment(new SignInFragment(), false);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_activity_linear_layout, fragment);

        if(addToBackStack){
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }

    @Override
    public void SuccessSignIn(String uId, String email, String password) {

        // Store in shared preferences
        SharedPreferencesManager preferences = new SharedPreferencesManager(this);
        preferences.LogInUser(email, password);

        // Set current user
        SessionManager.setUser(this, uId);

        // Go to notes activity
        Intent i = new Intent(AccountActivity.this, NotesActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void CreateAccount() {
        changeFragment(new SignUpFragment(), true);
    }

    @Override
    public void SuccessSignUp(String uId, String email, String password) {
        // Login when account created
        SuccessSignIn(uId, email, password);
    }
}
