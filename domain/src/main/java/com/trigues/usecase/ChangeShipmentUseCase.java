package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 22/04/2017.
 */

public class ChangeShipmentUseCase extends BaseUseCase<Boolean> implements Interactor<Shipment,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ChangeShipmentUseCase.ChangeShipmentUseCaseCallback callback;
    private Shipment shipment;

    @Inject
    public ChangeShipmentUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                                RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.BooleanCallback dataCallback = new RepositoryInterface.BooleanCallback() {

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Boolean returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Override
    public void run() {
        repository.changeShipment(shipment, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Shipment param, R defaultCallback) {
        this.callback = ((ChangeShipmentUseCase.ChangeShipmentUseCaseCallback) defaultCallback);
        this.shipment = param;
        executor.execute(this);
    }

    public interface ChangeShipmentUseCaseCallback extends DefaultCallback<Boolean> {
    }
}
