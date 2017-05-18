package trigues.com.trueke.presenter;

import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.LoginUseCase;
import com.trigues.usecase.LogoutUseCase;
import com.trigues.usecase.RegisterUseCase;

import javax.inject.Inject;

import trigues.com.trueke.view.LoginActivity;

/**
 * Created by mbaque on 15/03/2017.
 */

public class LoginPresenter {

    private LoginActivity view;
    private LoginUseCase loginUseCase;
    private LogoutUseCase logoutUseCase;
    private RegisterUseCase registerUseCase;
    @Inject
    public LoginPresenter(LoginActivity view,
                          LoginUseCase loginUseCase,
                          LogoutUseCase logoutUseCase,
                          RegisterUseCase registerUseCase) {

        this.view = view;
        this.loginUseCase = loginUseCase;
        this.logoutUseCase = logoutUseCase;
        this.registerUseCase = registerUseCase;

    }

    public void register(String nombre, String apellidos, String contraseña, String telefono, String mail, String fecha) {
        User user = new User(-1,telefono,
                nombre+" "+apellidos,contraseña,mail,fecha, "", 0,0,0,0);
        view.showProgress("Creando usuario...");
        registerUseCase.execute(user,new RegisterUseCase.RegisterUseCaseCallback(){

                    @Override
                    public void onError(ErrorBundle errorBundle) {
                        view.hideProgress();
                        view.onError(errorBundle.getErrorMessage());
                    }

                    @Override
                    public void onSuccess(Boolean returnParam) {
                        view.hideProgress();
                        view.onRegisterRetrieved(returnParam);
                    }
                });
    }

    public void login(String usuari, String password) {
        view.showProgress("Iniciando sesión...");
        loginUseCase.execute(new User(usuari, password), new LoginUseCase.LoginUseCaseCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.hideProgress();
                if(returnParam){
                    view.goToShowProductList();
                }
                else{
                    view.onError("Login incorrecto");
                }
            }
        });
    }
}
