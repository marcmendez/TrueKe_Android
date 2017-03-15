package trigues.com.trueke.presenter;

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
}
