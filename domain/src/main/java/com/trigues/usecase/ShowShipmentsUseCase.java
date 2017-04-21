package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Shipment;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Albert on 13/04/2017.
 */

public class ShowShipmentsUseCase extends BaseUseCase<List<Shipment>> implements Interactor<Integer,List<Shipment>> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ShowShipmentsUseCaseCallback callback;
    private Integer id;

    @Inject
    public ShowShipmentsUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                                RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.ShipmentCallback dataCallback = new RepositoryInterface.ShipmentCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(List<Shipment> returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };
    @Override
    public void run() {
        repository.showShipments(id,dataCallback);
    }

    @Override
    public <R extends DefaultCallback<List<Shipment>>> void execute(Integer param, R defaultCallback) {
        this.callback = ((ShowShipmentsUseCaseCallback) defaultCallback);
        this.id = param;
        executor.execute(this);
    }

    public interface ShowShipmentsUseCaseCallback extends DefaultCallback<List<Shipment>>{}
}
