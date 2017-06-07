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
 * Created by Marc PC on 07/06/2017.
 */

public class ShowProfileOtherUserUseCase extends BaseUseCase<User> implements Interactor<Integer,User> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ShowProfileUseCase.ShowProfileUseCaseCallback callback;

    private int userid;
    @Inject
    public ShowProfileOtherUserUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.showProfileOtherUser(userid, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<User>> void execute(Integer userid, R defaultCallback) {
        this.callback = ((ShowProfileUseCase.ShowProfileUseCaseCallback) defaultCallback);
        executor.execute(this);
        this.userid=userid;
    }

    public interface ShowProfileUseCaseCallback extends DefaultCallback<User>{}
}

