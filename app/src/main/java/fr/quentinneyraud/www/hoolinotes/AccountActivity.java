package fr.quentinneyraud.www.hoolinotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import fr.quentinneyraud.www.hoolinotes.Account.SignInFragment;
import fr.quentinneyraud.www.hoolinotes.Account.SignUpFragment;

public class AccountActivity extends AppCompatActivity implements SignInFragment.SignInListener {

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
    public void successSignIn() {
        Intent i = new Intent(AccountActivity.this, NotesActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void createAccount() {
        changeFragment(new SignUpFragment(), true);
    }
}
