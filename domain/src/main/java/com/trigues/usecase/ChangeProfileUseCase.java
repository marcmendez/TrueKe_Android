package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Parameter;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 14/04/2017.
 */

public class ChangeProfileUseCase extends BaseUseCase<Boolean> implements Interactor<Parameter,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ChangeProfileUseCase.ChangeProfileUseCaseCallback callback;
    private String type,value;

    @Inject
    public ChangeProfileUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.changeProfile(type,value, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Parameter p, R defaultCallback) {
        this.callback = ((ChangeProfileUseCase.ChangeProfileUseCaseCallback) defaultCallback);
        this.type = p.getType();
        this.value= p.getValue();
        executor.execute(this);
    }

    public interface ChangeProfileUseCaseCallback extends DefaultCallback<Boolean> {
    }
}

