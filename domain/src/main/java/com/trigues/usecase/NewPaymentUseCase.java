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
 * Created by Albert on 21/04/2017.
 */

public class NewPaymentUseCase extends BaseUseCase<Boolean> implements Interactor<Payment,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private NewPaymentUseCase.NewPaymentUseCaseCallback callback;
    private Payment payment;

    @Inject
    public NewPaymentUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.newPayment(payment, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Payment param, R defaultCallback) {
        this.callback = ((NewPaymentUseCase.NewPaymentUseCaseCallback) defaultCallback);
        this.payment = param;
        executor.execute(this);
    }

    public interface NewPaymentUseCaseCallback extends DefaultCallback<Boolean> {
    }
}
