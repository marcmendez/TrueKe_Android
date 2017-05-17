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
 * Created by Albert on 13/05/2017.
 */

public class ChangeProfileImageUseCase extends BaseUseCase<Boolean> implements Interactor<String, Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ChangeProfileImageUseCase.ChangeProfileImageUseCaseCallback callback;
    private String image;

    @Inject
    public ChangeProfileImageUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
        repository.changeProfileImage(image, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(String image, R defaultCallback) {
        this.callback = ((ChangeProfileImageUseCase.ChangeProfileImageUseCaseCallback) defaultCallback);
        this.image = image;
        executor.execute(this);
    }

    public interface ChangeProfileImageUseCaseCallback extends DefaultCallback<Boolean> {
    }
}
