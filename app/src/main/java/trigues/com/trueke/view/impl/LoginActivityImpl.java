package trigues.com.trueke.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.LoginPresenter;
import trigues.com.trueke.view.LoginActivity;
import trigues.com.trueke.view.fragment.LoginFragImpl;
import trigues.com.trueke.view.fragment.RegisterFragImpl;

public class LoginActivityImpl extends BaseActivityImpl implements LoginActivity {

    @Inject
    LoginPresenter presenter;
    LoginFragImpl loginFrag;
    RegisterFragImpl registerFrag;
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
        loginFrag = new LoginFragImpl();
        registerFrag = new RegisterFragImpl();

        openLoginFragment();

    }

    public void openRegisterFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, registerFrag)
                .commit();
    }

    public void openLoginFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_view, loginFrag)
                .commit();
    }

    public void goToShowProductList(){
        startActivity(new Intent(this, ShowProductsActivityImpl.class));
    }

    public void onLoginPressed(String usuari, String password){
        presenter.login(usuari, password);
    }

    public void onRegisterPressed(String nombre, String apellidos, String contraseña, String telefono, String mail, String fecha ){
        presenter.register(nombre,apellidos,contraseña,telefono,mail,fecha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openLoginFragment();
        return true;
    }



    @Override
    public void onRegisterRetrieved(Boolean returnParam) {
        RegisterFragImpl registerFrag = (RegisterFragImpl)
       getSupportFragmentManager().findFragmentById(R.id.fragment_view);
        registerFrag.onRegisterRetrieved(returnParam);
    }
}
