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
 * Created by Albert on 17/05/2017.
 */

public class ChangeImageUserUseCase extends BaseUseCase<Boolean> implements Interactor<String,Boolean> {
        private final RepositoryInterface repository;
        private final ThreadExecutor executor;
        private ChangeProfileUserImageUseCaseCallback callback;
        private String image_path;

        @Inject
        public ChangeImageUserUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
            repository.changeProfileUserImage(image_path, dataCallback);
        }

        @Override
        public <R extends DefaultCallback<Boolean>> void execute(String img, R defaultCallback) {
            this.callback = ((ChangeImageUserUseCase.ChangeProfileUserImageUseCaseCallback) defaultCallback);
            this.image_path=img;
            executor.execute(this);
        }

        public interface ChangeProfileUserImageUseCaseCallback extends DefaultCallback<Boolean> {
        }
}
