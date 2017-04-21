package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Payment;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 21/04/2017.
 */

public class ChangePaymentUseCase extends BaseUseCase<Boolean> implements Interactor<Payment,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ChangePaymentUseCase.ChangePaymentUseCaseCallback callback;
    private Payment payment;

    @Inject
    public ChangePaymentUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.changePayment(payment, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Payment param, R defaultCallback) {
        this.callback = ((ChangePaymentUseCase.ChangePaymentUseCaseCallback) defaultCallback);
        this.payment = param;
        executor.execute(this);
    }

    public interface ChangePaymentUseCaseCallback extends DefaultCallback<Boolean> {
    }
}
