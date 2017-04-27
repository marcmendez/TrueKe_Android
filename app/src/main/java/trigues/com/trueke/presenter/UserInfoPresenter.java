package trigues.com.trueke.presenter;

import com.trigues.entity.Parameter;
import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ChangePaymentUseCase;
import com.trigues.usecase.ChangeProfileUseCase;
import com.trigues.usecase.ChangeShipmentUseCase;
import com.trigues.usecase.DeletePaymentUseCase;
import com.trigues.usecase.DeleteShipmentUseCase;
import com.trigues.usecase.DeleteUserUseCase;
import com.trigues.usecase.NewPaymentUseCase;
import com.trigues.usecase.NewShipmentUseCase;
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
    private NewPaymentUseCase newPaymentUseCase;
    private ChangePaymentUseCase changePaymentUseCase;
    private DeletePaymentUseCase deletePaymentUseCase;
    private DeleteShipmentUseCase deleteShipmentUseCase;
    private NewShipmentUseCase newShipmentUseCase;
    private ChangeShipmentUseCase changeShipmentUseCase;

    @Inject
    public UserInfoPresenter(UserProfileActivity view,
                             ShowProfileUseCase showProfileUseCase,
                             ShowPaymentsUseCase showPaymentsUseCase,
                             ShowShipmentsUseCase showShipmentsUseCase,
                             ChangeProfileUseCase changeProfileUseCase,
                             DeleteUserUseCase deleteUserUseCase,
                             NewPaymentUseCase newPaymentUseCase,
                             ChangePaymentUseCase changePaymentUseCase,
                             DeletePaymentUseCase deletePaymentUseCase, DeleteShipmentUseCase deleteShipmentUseCase, NewShipmentUseCase newShipmentUseCase, ChangeShipmentUseCase changeShipmentUseCase) {
        this.view = view;
        this.showProfileUseCase=showProfileUseCase;
        this.showPaymentsUseCase = showPaymentsUseCase;
        this.showShipmentsUseCase = showShipmentsUseCase;
        this.changeProfileUseCase = changeProfileUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.newPaymentUseCase = newPaymentUseCase;
        this.changePaymentUseCase = changePaymentUseCase;
        this.deletePaymentUseCase = deletePaymentUseCase;
        this.deleteShipmentUseCase = deleteShipmentUseCase;
        this.newShipmentUseCase = newShipmentUseCase;
        this.changeShipmentUseCase = changeShipmentUseCase;
    }

    public void showProfile(){
        showProfileUseCase.execute(null,new ShowProfileUseCase.ShowProfileUseCaseCallback(){

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
        showPaymentsUseCase.execute(-1, new ShowPaymentsUseCase.ShowPaymentsUseCaseCallback(){

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
        showShipmentsUseCase.execute(-1, new ShowShipmentsUseCase.ShowShipmentsUseCaseCallback(){

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
    public void changeProfile(String type,String value){
        changeProfileUseCase.execute(new Parameter(type,value), new ChangeProfileUseCase.ChangeProfileUseCaseCallback(){

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
    public void newPayment(Payment payment){
        newPaymentUseCase.execute(payment, new NewPaymentUseCase.NewPaymentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onNewPaymentCreated(returnParam);
            }
        });
    }

    public void changePayment(Payment payment){
        changePaymentUseCase.execute(payment, new ChangePaymentUseCase.ChangePaymentUseCaseCallback(){

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
    public void changeShipment(Shipment shipment){
        changeShipmentUseCase.execute(shipment, new ChangeShipmentUseCase.ChangeShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onChangeShipmentRetrieved(returnParam);
            }
        });
    }
    public void deletePayment(Integer payment_id){
        deletePaymentUseCase.execute(payment_id, new DeletePaymentUseCase.DeletePaymentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.OnPaymentDeleted(returnParam);
            }
        });
    }
    public void deleteShipment(Integer shipment_id){
        int user_id=1;
        deleteShipmentUseCase.execute(user_id, new DeleteShipmentUseCase.DeleteShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.OnShipmentDeleted(returnParam);
            }
        });
    }
    public void newShipment(Shipment shipment){
        newShipmentUseCase.execute(shipment, new NewShipmentUseCase.NewShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onNewShipmentCreated(returnParam);
            }
        });
    }

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
