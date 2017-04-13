package trigues.com.trueke.presenter;

import com.trigues.entity.Payment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ShowProfileUseCase;

import javax.inject.Inject;
import trigues.com.trueke.view.UserProfileActivity;

/**
 * Created by Albert on 07/04/2017.
 */

public class UserInfoPresenter {
    private UserProfileActivity view;
    private ShowProfileUseCase showProfileUseCase;

    @Inject
    public UserInfoPresenter(UserProfileActivity view,
                             ShowProfileUseCase showProfileUseCase) {
        this.view = view;
        this.showProfileUseCase=showProfileUseCase;
    }

    public void showProfile(){
        int userID=1;
        showProfileUseCase.execute(userID, new ShowProfileUseCase.ShowProfileUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(User returnParam) {
                view.onProfileRetrieved(returnParam);
            }
        });
    }

    public void showPaymentInfo(Integer userID){}
    public void showShipmentInfo(Integer userID){}
    public void changeProfile(User user){}
    public void changePaymentInfo(Payment payment){}
    //falta changeShipmentInfo
}
