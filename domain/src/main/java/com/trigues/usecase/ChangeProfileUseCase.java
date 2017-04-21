package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.User;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 14/04/2017.
 */

public class ChangeProfileUseCase extends BaseUseCase<Boolean> implements Interactor<User,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ChangeProfileUseCase.ChangeProfileUseCaseCallback callback;
    private User user;

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
        repository.changeProfile(user, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(User param, R defaultCallback) {
        this.callback = ((ChangeProfileUseCase.ChangeProfileUseCaseCallback) defaultCallback);
        this.user = param;
        executor.execute(this);
    }

    public interface ChangeProfileUseCaseCallback extends DefaultCallback<Boolean> {
    }
}

