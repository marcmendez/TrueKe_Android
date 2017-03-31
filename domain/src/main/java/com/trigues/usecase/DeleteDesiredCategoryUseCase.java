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
 * Created by mbaque on 27/03/2017.
 */

public class DeleteDesiredCategoryUseCase extends BaseUseCase<Void> implements Interactor<Product, Void> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private DeleteDesiredCategoryUseCase.DeleteDesiredCategoryCallback callback;

    private Product product;

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Void aVoid) {
            notifyOnSuccess(aVoid, callback);
        }
    };

    @Inject
    public DeleteDesiredCategoryUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(Product product, R defaultCallback) {
        this.callback = ((DeleteDesiredCategoryUseCase.DeleteDesiredCategoryCallback) defaultCallback);
        this.product = product;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.deleteDesiredCategory(product, dataCallback);
    }

    public interface DeleteDesiredCategoryCallback extends DefaultCallback<Void>{}
}
