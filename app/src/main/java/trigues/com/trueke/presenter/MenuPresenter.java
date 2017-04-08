package trigues.com.trueke.presenter;

import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.LogoutUseCase;

import javax.inject.Inject;

import trigues.com.trueke.view.MenuActivity;

/**
 * Created by mbaque on 18/03/2017.
 */

public class MenuPresenter {

    private MenuActivity view;
    private LogoutUseCase logoutUseCase;

    @Inject
    public MenuPresenter(MenuActivity view, LogoutUseCase logoutUseCase) {
        this.view = view;
        this.logoutUseCase = logoutUseCase;
    }

    public void logout() {
        logoutUseCase.execute(null, new LogoutUseCase.LogoutUseCaseCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Void returnParam) {
                view.onLogout();
            }
        });
    }
}
