package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Alba on 05/04/2017.
 */

public class AddProductUseCase extends BaseUseCase<Boolean> implements Interactor<Product,Boolean> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private AddProductUseCase.AddProductCallback callback;

    private Product product;

    @Inject
    public AddProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
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
    public <R extends DefaultCallback<Boolean>> void execute(Product param, R defaultCallback) {
        this.callback = ((AddProductUseCase.AddProductCallback) defaultCallback);
        this.product = param;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.addProduct(product, dataCallback);
    }

    public interface AddProductCallback extends DefaultCallback<Boolean> {}
}
