package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by marc on 19/05/17.
 */

public class GetProductUseCase extends BaseUseCase<List<Product>> implements Interactor<List<Integer>,List<Product>> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetProductCallback callback;


    private List<Integer> productId;
    private List<Product> products = new ArrayList<>();;
    int count =0;


    RepositoryInterface.ProductCallback dataCallback = new RepositoryInterface.ProductCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Product returnParam) {
            products.add(returnParam);
            if (products.size() == productId.size()) {
                notifyOnSuccess(products, callback);

            }
            else run();
        }
    };

    @Inject
    public GetProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<Product>>> void execute(List<Integer> productId, R defaultCallback) {
        this.callback = ((GetProductCallback) defaultCallback);
        this.productId = productId;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getProductInfo(productId.get(count), dataCallback);
        count++;
    }

    public interface GetProductCallback extends DefaultCallback<List<Product>> {
    }
}
