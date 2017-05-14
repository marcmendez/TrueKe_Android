package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Alba on 05/04/2017.
 */

public class AddImagesProductUseCase extends BaseUseCase<Boolean> implements Interactor<String,Boolean> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private AddImagesProductUseCase.AddImagesProductCallback callback;

    private String image_md5;


    @Inject
    public AddImagesProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                             RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.BooleanCallback dataCallback = new RepositoryInterface.BooleanCallback(){
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(Boolean returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(String param,R defaultCallback) {
        this.callback = ((AddImagesProductUseCase.AddImagesProductCallback) defaultCallback);
        this.image_md5 = param;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.addImagesProduct(image_md5, dataCallback);
    }

    public interface AddImagesProductCallback extends DefaultCallback<Boolean> {}
}
