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
 * Created by Albert on 07/04/2017.
 */

public class ShowProfileUseCase extends BaseUseCase<User> implements Interactor<Integer,User>{
        private final RepositoryInterface repository;
        private final ThreadExecutor executor;
        private ShowProfileUseCase.ShowProfileUseCaseCallback callback;
        private Integer id;

    @Inject
    public ShowProfileUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                              RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.UserCallback dataCallback = new RepositoryInterface.UserCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(User returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };
    @Override
    public void run() {
        repository.showProfile(id,dataCallback);
    }

    @Override
    public <R extends DefaultCallback<User>> void execute(Integer param, R defaultCallback) {
        this.callback = ((ShowProfileUseCase.ShowProfileUseCaseCallback) defaultCallback);
        this.id = param;
        executor.execute(this);
    }

    public interface ShowProfileUseCaseCallback extends DefaultCallback<User>{}
}
