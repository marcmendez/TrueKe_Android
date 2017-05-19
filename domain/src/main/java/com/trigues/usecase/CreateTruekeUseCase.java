package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.TruekeData;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 19/05/2017.
 */

public class CreateTruekeUseCase extends BaseUseCase<Void> implements Interactor<String, Void> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private CreateTruekeUseCase.CreateTruekeCallback callback;
    private String chatID;

    @Inject
    public CreateTruekeUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.createTrueke(chatID, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(String chatID, R defaultCallback) {
        this.callback = ((CreateTruekeUseCase.CreateTruekeCallback) defaultCallback);
        this.chatID=chatID;
        executor.execute(this);
    }


    public interface CreateTruekeCallback extends DefaultCallback<Void>{}

}