package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.view.impl.LoginActivityImpl;

/**
 * Created by mbaque on 31/03/2017.
 */

public class LoginFragImpl extends Fragment {

    @BindView(R.id.btnLogin)
    Button buttonLogin;

    @BindView(R.id.btnRegister)
    Button buttonRegister;

    @BindView(R.id.login_email_edittext)
    EditText usernameText;

    @BindView(R.id.login_password_edittext)
    EditText passwordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, rootView);

        buttonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usernameString = usernameText.getText().toString();
                String passwordString = passwordText.getText().toString();

                // Pasar los datos hacia abajo;
                ((LoginActivityImpl) getActivity()).onLoginPressed(usernameString, passwordString);
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

}
