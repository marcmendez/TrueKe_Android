package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;
import com.trigues.exception.ErrorBundle;

import javax.inject.Inject;

/**
 * Created by Alba on 11/05/2017.
 */

public class AddImagesUseCase extends BaseUseCase<String> implements Interactor<String,String> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private AddImagesCallback callback;

    private String image_base64;

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
    public AddImagesUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<String>> void execute(String image_base64, R defaultCallback) {
        this.callback = ((AddImagesCallback) defaultCallback);
        this.image_base64 = image_base64;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.addImages(image_base64, dataCallback);
    }

    public interface AddImagesCallback extends DefaultCallback<String>{}

}