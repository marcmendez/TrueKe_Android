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
 * Created by Albert on 19/04/2017.
 */

public class DeleteUserUseCase extends BaseUseCase<Boolean> implements Interactor<Integer,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private DeleteUserUseCase.DeleteUserUseCaseCallback callback;
    private int user_id;

    @Inject
    public DeleteUserUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.deleteUser(user_id, dataCallback);
    }


    @Override
    public <R extends DefaultCallback<Boolean>> void execute(Integer param, R defaultCallback) {
        this.callback = ((DeleteUserUseCase.DeleteUserUseCaseCallback) defaultCallback);
        this.user_id = param;
        executor.execute(this);
    }

    public interface DeleteUserUseCaseCallback extends DefaultCallback<Boolean>{
    }
}
