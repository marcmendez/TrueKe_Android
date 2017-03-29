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
 * Created by Marc on 22/03/2017.
 */



public class ShowProductsUseCase extends BaseUseCase<Product> implements Interactor<Integer, Product> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private showProductsCallback callback;

    private int userID;

    RepositoryInterface.showProductsCallback dataCallback = new RepositoryInterface.showProductsCallback() {
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
    public  ShowProductsUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Product>> void execute(Integer userID, R defaultCallback) {
        this.callback = ((showProductsCallback) defaultCallback);
        this.userID= userID;
        executor.execute(this);

    }
    @Override
    public void run() {
        repository.showProducts(userID, dataCallback);
    }

    public interface showProductsCallback extends DefaultCallback<Product>{}


}
