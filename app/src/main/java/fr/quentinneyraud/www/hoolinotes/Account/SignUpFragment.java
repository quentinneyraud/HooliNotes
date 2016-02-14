package fr.quentinneyraud.www.hoolinotes.Account;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Map;

import fr.quentinneyraud.www.hoolinotes.R;
import fr.quentinneyraud.www.hoolinotes.Utils.DeviceInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private ActionProcessButton submitButton;
    private MaterialAutoCompleteTextView emailEditText;
    private MaterialEditText passwordEditText;

    SignUpListener signUpListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpListener) {
            signUpListener = (SignUpListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignUpListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Get UI
        emailEditText = (MaterialAutoCompleteTextView) view.findViewById(R.id.sign_up_email_edit_text);
        passwordEditText = (MaterialEditText) view.findViewById(R.id.sign_up_password_edit_text);
        submitButton = (ActionProcessButton) view.findViewById(R.id.sign_up_submit_button);

        // Listener
        submitButton.setMode(ActionProcessButton.Mode.ENDLESS);
        submitButton.setOnClickListener(this);

        // Autocomplete email
        emailEditText.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, DeviceInfo.getAccounts(getContext())));

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up_submit_button:
                SubmitSignUpForm();
                break;
        }
    }

    private void SubmitSignUpForm(){
        submitButton.setProgress(1);

        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        String firebaseUrl = this.getResources().getString(R.string.firebase_base);

        new Firebase(firebaseUrl).createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                submitButton.setProgress(0);
                signUpListener.SuccessSignUp((String) result.get("uid"), email, password);
            }
            @Override
            public void onError(FirebaseError firebaseError) {
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

    public interface SignUpListener{
        void SuccessSignUp(String uId, String email, String password);
    }

}
