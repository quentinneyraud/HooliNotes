package fr.quentinneyraud.www.hoolinotes.Account;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.User.SessionManager;
import fr.quentinneyraud.www.hoolinotes.Utils.DeviceInfo;


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
        emailEditText.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, DeviceInfo.getAccounts(getContext())));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInListener) {
            signInListener = (SignInListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignInListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_submit_button:
                SubmitLoginForm();
                break;
            case R.id.sign_in_create_account_edit_text:
                signInListener.CreateAccount();
                break;
        }
    }

    private void SubmitLoginForm(){
        submitButton.setProgress(1);

        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        SessionManager.auth(getContext(), email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                submitButton.setProgress(0);
                signInListener.SuccessSignIn(authData.getUid(), email, password);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                submitButton.setProgress(0);
                Integer errorCode = firebaseError.getCode();
                if(errorCode == -15){
                    emailEditText.setError("Email incorrecte");
                }else if(errorCode == -16){
                    passwordEditText.setError("Mot de passe incorrecte");
                }
            }
        });
    }

    public interface SignInListener{
        void SuccessSignIn(String uId, String email, String password);
        void CreateAccount();
    }
}
