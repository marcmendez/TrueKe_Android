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
 * Created by mbaque on 24/03/2017.
 */

public class GetUserProductDetailsUseCase extends BaseUseCase<Product> implements Interactor<Integer,Product> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetUserProductDetailsCallback callback;

    private int productId;

    RepositoryInterface.GetUserProductDetailsCallback dataCallback = new RepositoryInterface.GetUserProductDetailsCallback() {
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
    public GetUserProductDetailsUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Product>> void execute(Integer productId, R defaultCallback) {
        this.callback = ((GetUserProductDetailsCallback) defaultCallback);
        this.productId = productId;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getUserProductDetails(productId, dataCallback);
    }

    public interface GetUserProductDetailsCallback extends DefaultCallback<Product>{}
}
