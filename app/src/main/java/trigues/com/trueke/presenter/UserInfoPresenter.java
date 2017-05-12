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
        view.showProgress("Cargando perfil...");
        showProfileUseCase.execute(null,new ShowProfileUseCase.ShowProfileUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(User returnParam) {
                view.onProfileRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }

    public void showPayments(){
        view.showProgress("Cargando métodos de pago...");
        showPaymentsUseCase.execute(-1, new ShowPaymentsUseCase.ShowPaymentsUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<Payment> returnParam) {
                view.onPaymentRetrieved(returnParam);
                view.hideProgress();
            }
        });

    }
    public void showShipments(){
        view.showProgress("Cargando direcciones...");
        showShipmentsUseCase.execute(-1, new ShowShipmentsUseCase.ShowShipmentsUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(List<Shipment> returnParam) {
                view.onShipmentRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }
    public void changeProfile(String type,String value){
        view.showProgress("Editando perfil...");
        changeProfileUseCase.execute(new Parameter(type,value), new ChangeProfileUseCase.ChangeProfileUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onChangeProfileRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }
    public void newPayment(Payment payment){
        view.showProgress("Creando método de pago...");
        newPaymentUseCase.execute(payment, new NewPaymentUseCase.NewPaymentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onNewPaymentCreated(returnParam);
                view.hideProgress();
            }
        });
    }

    public void changePayment(Payment payment){
        view.showProgress("Editando método de pago...");
        changePaymentUseCase.execute(payment, new ChangePaymentUseCase.ChangePaymentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onChangeProfileRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }
    public void changeShipment(Shipment shipment){
        view.showProgress("Editando método de pago...");
        changeShipmentUseCase.execute(shipment, new ChangeShipmentUseCase.ChangeShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onChangeShipmentRetrieved(returnParam);
                view.hideProgress();
            }
        });
    }
    public void deletePayment(Integer payment_id){
        view.showProgress("Eliminando método de pago...");
        deletePaymentUseCase.execute(payment_id, new DeletePaymentUseCase.DeletePaymentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.OnPaymentDeleted(returnParam);
                view.hideProgress();
            }
        });
    }
    public void deleteShipment(Integer shipment_id){
        view.showProgress("Eliminando dirección...");
        int user_id=1;
        deleteShipmentUseCase.execute(user_id, new DeleteShipmentUseCase.DeleteShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.hideProgress();
                view.onShipmentDeleted(returnParam);
            }
        });
    }
    public void newShipment(Shipment shipment){
        view.showProgress("Creando dirección...");
        newShipmentUseCase.execute(shipment, new NewShipmentUseCase.NewShipmentUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.onNewShipmentCreated(returnParam);
                view.hideProgress();
            }
        });
    }

    public void deleteUser() {
        view.showProgress("Eliminando usuario...");
        int user_id=1;
        deleteUserUseCase.execute(user_id, new DeleteUserUseCase.DeleteUserUseCaseCallback(){

            @Override
            public void onError(ErrorBundle errorBundle) {
                view.hideProgress();
                view.onError(errorBundle.getErrorMessage());
            }

            @Override
            public void onSuccess(Boolean returnParam) {
                view.hideProgress();
                view.OnUserDeleted(returnParam);
            }
        });
    }
}
