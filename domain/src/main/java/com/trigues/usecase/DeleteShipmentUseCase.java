package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 22/04/2017.
 */

public class DeleteShipmentUseCase extends BaseUseCase<Boolean> implements Interactor<Integer,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private DeleteShipmentUseCase.DeleteShipmentUseCaseCallback callback;
    private int shipment_id;

    @Inject
    public DeleteShipmentUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.deleteShipment(shipment_id, dataCallback);
    }


    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Integer param, R defaultCallback) {
        this.callback = ((DeleteShipmentUseCase.DeleteShipmentUseCaseCallback) defaultCallback);
        this.shipment_id = param;
        executor.execute(this);
    }

    public interface DeleteShipmentUseCaseCallback extends DefaultCallback<Boolean>{
    }
}