package trigues.com.trueke.presenter;

import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ChangeProfileUseCase;
import com.trigues.usecase.DeleteUserUseCase;
import com.trigues.usecase.ShowPaymentsUseCase;
import com.trigues.usecase.ShowProfileUseCase;
import com.trigues.usecase.ShowShipmentsUseCase;

import java.util.List;

import javax.inject.Inject;
import trigues.com.trueke.view.UserProfileActivity;

/**
 * Created by Albert on 07/04/2017.
 */

public class UserInfoPresenter {
    private UserProfileActivity view;
    private ShowProfileUseCase showProfileUseCase;
    private ShowPaymentsUseCase showPaymentsUseCase;
    private ShowShipmentsUseCase showShipmentsUseCase;
    private ChangeProfileUseCase changeProfileUseCase;
    private DeleteUserUseCase deleteUserUseCase;

    @Inject
    public UserInfoPresenter(UserProfileActivity view,
                             ShowProfileUseCase showProfileUseCase,
                             ShowPaymentsUseCase showPaymentsUseCase,
                             ShowShipmentsUseCase showShipmentsUseCase,
                             ChangeProfileUseCase changeProfileUseCase,
                             DeleteUserUseCase deleteUserUseCase) {
        this.view = view;
        this.showProfileUseCase=showProfileUseCase;
        this.showPaymentsUseCase = showPaymentsUseCase;
        this.showShipmentsUseCase = showShipmentsUseCase;
        this.changeProfileUseCase = changeProfileUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
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

    public void showPayments(){
        int userID=1;
        showPaymentsUseCase.execute(userID, new ShowPaymentsUseCase.ShowPaymentsUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<Payment> returnParam) {
                view.onPaymentRetrieved(returnParam);
            }
        });

    }
    public void showShipments(){
        int userID=1;
        showShipmentsUseCase.execute(userID, new ShowShipmentsUseCase.ShowShipmentsUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<Shipment> returnParam) {
                view.onShipmentRetrieved(returnParam);
            }
        });
    }
    public void changeProfile(User user){
        changeProfileUseCase.execute(user, new ChangeProfileUseCase.ChangeProfileUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onChangeProfileRetrieved(returnParam);
            }
        });
    }
    public void changePayment(Payment payment){}
    public void changeShipment(Shipment shipment){}
    public void deletePayment(Integer payment_id){}
    public void deleteShipment(Integer shipment_id){}
    public void newPayment(Payment payment){}
    public void newShipment(Shipment shipment){}

    public void deleteUser() {
        int user_id=1;
        deleteUserUseCase.execute(user_id, new DeleteUserUseCase.DeleteUserUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.OnUserDeleted(returnParam);
            }
        });
    }
}
