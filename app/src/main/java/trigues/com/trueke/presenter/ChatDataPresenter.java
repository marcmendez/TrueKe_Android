package trigues.com.trueke.presenter;

import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.exception.ErrorBundle;
import com.trigues.usecase.ShowPaymentsUseCase;
import com.trigues.usecase.ShowShipmentsUseCase;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by Albert on 18/05/2017.
 */

public class ChatDataPresenter {
    private ShowPaymentsUseCase showPaymentsUseCase;
    private ShowShipmentsUseCase showShipmentsUseCase;
    private ChatListActivity view;

    @Inject
    public ChatDataPresenter(ShowPaymentsUseCase showPaymentsUseCase, ShowShipmentsUseCase showShipmentsUseCase, ChatListActivity view){

        this.showPaymentsUseCase = showPaymentsUseCase;
        this.showShipmentsUseCase = showShipmentsUseCase;
        this.view = view;
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
}
