package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 13/04/2017.
 */

public class ShowPaymentInfoUseCase extends BaseUseCase<Payment> implements Interactor<Integer,Payment> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ShowPaymentInfoUseCase.ShowPaymentInfoUseCaseCallback callback;
    private Integer id;

    @Inject
    public ShowPaymentInfoUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                                  RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.PaymentCallback dataCallback = new RepositoryInterface.PaymentCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(Payment returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };
    @Override
    public void run() {
        repository.showPaymentInfo(id,dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Payment>> void execute(Integer param, R defaultCallback) {
        this.callback = ((ShowPaymentInfoUseCase.ShowPaymentInfoUseCaseCallback) defaultCallback);
        this.id = param;
        executor.execute(this);
    }

    public interface ShowPaymentInfoUseCaseCallback extends DefaultCallback<Payment>{}
}
