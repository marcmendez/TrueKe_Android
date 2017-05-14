package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Alba on 11/05/2017.
 */

public class GetImagesUseCase extends BaseUseCase<String> implements Interactor<String,String> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetImagesCallback callback;

    private String image_md5;

    RepositoryInterface.ImagesCallback dataCallback = new RepositoryInterface.ImagesCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(String returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetImagesUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<String>> void execute(String image_md5, R defaultCallback) {
        this.callback = ((GetImagesCallback) defaultCallback);
        this.image_md5 = image_md5;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getImages(image_md5, dataCallback);
    }

    public interface GetImagesCallback extends DefaultCallback<String>{}

}