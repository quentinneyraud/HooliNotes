package fr.quentinneyraud.www.hoolinotes.Account;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener {

    private ActionProcessButton submitButton;
    private MaterialAutoCompleteTextView emailEditText;
    private MaterialEditText passwordEditText;
    private TextView creatAccountTextView;

    SignInListener signInListener;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        // Get UI
        emailEditText = (MaterialAutoCompleteTextView) view.findViewById(R.id.sign_in_email_edit_text);
        passwordEditText = (MaterialEditText) view.findViewById(R.id.sign_in_password_edit_text);
        submitButton = (ActionProcessButton) view.findViewById(R.id.sign_in_submit_button);
        creatAccountTextView = (TextView) view.findViewById(R.id.sign_in_create_account_edit_text);

        // Listener
        submitButton.setMode(ActionProcessButton.Mode.ENDLESS);
        submitButton.setOnClickListener(this);
        creatAccountTextView.setOnClickListener(this);

        // Autocomplete email
        emailEditText.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, getAccounts()));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInListener) {
            signInListener = (SignInListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    private ArrayList<String> getAccounts(){
        final Account[] accounts = AccountManager.get(getContext()).getAccounts();
        final Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        return new ArrayList<String>(emailSet);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_submit_button:
                submitLoginForm();
                break;
            case R.id.sign_in_create_account_edit_text:
                signInListener.createAccount();
                break;
        }
    }

    private void submitLoginForm(){
        submitButton.setProgress(1);

        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        SessionManager.auth(getContext(), email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                submitButton.setProgress(0);
                signInListener.successSignIn(authData.getUid(), email, password);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                submitButton.setProgress(0);
                // gérer erreurs
                Log.d("CONNECT", firebaseError.toString());
            }
        });
    }

    public interface SignInListener{
        void successSignIn(String uId, String email, String password);
        void createAccount();
    }
}
