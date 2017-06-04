package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.TruekeData;
import com.trigues.entity.VoteData;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Albert on 04/06/2017.
 */

public class VoteUseCase extends BaseUseCase<Void> implements Interactor<VoteData, Void> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private VoteUseCase.VoteUseCaseCallback callback;
    private VoteData votedata;

    @Inject
    public VoteUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.voteTrueke(votedata, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(VoteData td, R defaultCallback) {
        this.callback = ((VoteUseCase.VoteUseCaseCallback) defaultCallback);
        this.votedata = td;
        executor.execute(this);
    }


    public interface VoteUseCaseCallback extends DefaultCallback<Void>{}

}