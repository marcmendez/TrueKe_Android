package trigues.com.trueke.view.impl;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.view.LoginActivity;

public class LoginActivityImpl extends BaseActivityImpl implements LoginActivity {

    @Inject
    LoginPresenter presenter;
    Fragment f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);
        f = new RegisterFragImpl();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, f)
                .commit();
    }

    public void onRegisterPressed(String nombre, String apellidos, String contraseña, String telefono, String mail, String fecha ){
        presenter.register(nombre,apellidos,contraseña,telefono,mail,fecha);
    }

    //en cas de tornar enrere treiem el fragment de sobre del login
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onRegisterRetrieved(Boolean returnParam) {
        RegisterFragImpl registerFrag = (RegisterFragImpl)
       getSupportFragmentManager().findFragmentById(R.id.fragment_view);
        registerFrag.onRegisterRetrieved(returnParam);

    }
}
