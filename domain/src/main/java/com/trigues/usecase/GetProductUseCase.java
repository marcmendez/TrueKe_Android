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
 * Created by marc on 19/05/17.
 */

public class GetProductUseCase extends BaseUseCase<Product> implements Interactor<Integer,Product> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetProductCallback callback;

    private int productId;

    RepositoryInterface.ProductCallback dataCallback = new RepositoryInterface.ProductCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Product returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Product>> void execute(Integer productId, R defaultCallback) {
        this.callback = ((GetProductCallback) defaultCallback);
        this.productId = productId;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getProductInfo(productId, dataCallback);
    }

    public interface GetProductCallback extends DefaultCallback<Product> {
    }
}
