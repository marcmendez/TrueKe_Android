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
 * Created by Marc on 22/03/2017.
 */



public class GetUserProductsUseCase extends BaseUseCase<List<Product>> implements Interactor<Integer, List<Product> > {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private UserProductsListCallback callback;

    private int userID;

    RepositoryInterface.ProductListCallback dataCallback = new RepositoryInterface.ProductListCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(List<Product> returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetUserProductsUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<Product>>> void execute(Integer userID, R defaultCallback) {
        this.callback = ((UserProductsListCallback) defaultCallback);
        this.userID= userID;
        executor.execute(this);

    }
    @Override
    public void run() {
        repository.showProducts(userID, dataCallback);
    }



    public interface UserProductsListCallback extends DefaultCallback<List<Product>>{}


}
