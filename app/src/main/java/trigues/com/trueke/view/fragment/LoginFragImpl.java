package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import trigues.com.trueke.R;
import trigues.com.trueke.view.impl.LoginActivityImpl;

/**
 * Created by mbaque on 31/03/2017.
 */

public class LoginFragImpl extends Fragment {

    TextInputLayout usernameWrapper;
    TextInputLayout passwordWrapper;

    Button buttonLogin;
    Button buttonRegister;

    EditText usernameText;
    EditText passwordText;


    public LoginFragImpl() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        /*usernameWrapper = (TextInputLayout) rootView.findViewById(R.id.usernameWrapper);
        passwordWrapper = (TextInputLayout) rootView.findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Email address or phone number");
        passwordWrapper.setHint("Password");*/

        usernameText = (EditText) rootView.findViewById(R.id.username);
        passwordText = (EditText) rootView.findViewById(R.id.password);
        buttonLogin = (Button) rootView.findViewById(R.id.btnLogin);
        buttonRegister = (Button) rootView.findViewById(R.id.btnRegister);

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameString = usernameText.getText().toString();
                String passwordString = passwordText.getText().toString();

                Log.d("VALORS", "username = " + usernameString + "   password = " + passwordString);

                // Pasar los datos hacia abajo;
                ((LoginActivityImpl) getActivity()).onLoginPressed(usernameString, passwordString);

                //hideKeyboard();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                ((LoginActivityImpl) getActivity()).openRegisterFragment();

            }
        });


        return rootView;


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
