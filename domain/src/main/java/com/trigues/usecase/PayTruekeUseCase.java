package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.TruekeData;
import com.trigues.entity.TruekePaymentData;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 19/05/2017.
 */

public class PayTruekeUseCase extends BaseUseCase<Void> implements Interactor<TruekePaymentData, Void> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private PayTruekeUseCase.PayTruekeCallback callback;
    private TruekePaymentData truekedata;

    @Inject
    public PayTruekeUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                                  RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(Void returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };


    @Override
    public void run() {
        repository.payTrueke(truekedata, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(TruekePaymentData td, R defaultCallback) {
        this.callback = ((PayTruekeUseCase.PayTruekeCallback) defaultCallback);
        this.truekedata = td;
        executor.execute(this);
    }


    public interface PayTruekeCallback extends DefaultCallback<Void>{}

}