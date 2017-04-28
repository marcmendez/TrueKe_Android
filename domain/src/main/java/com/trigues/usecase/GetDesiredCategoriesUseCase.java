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
 * Created by marc on 28/04/17.
 */

public class GetDesiredCategoriesUseCase  extends BaseUseCase<List<String>> implements Interactor<Integer,List<String>> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetDesiredCategoriesCallback callback;

    private int productId;

    RepositoryInterface.StringListCallback dataCallback = new RepositoryInterface.StringListCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(List<String> returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetDesiredCategoriesUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<String>>> void execute(Integer productId, R defaultCallback) {
        this.callback = ((GetDesiredCategoriesCallback) defaultCallback);
        this.productId = productId;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getDesiredCategories(productId, dataCallback);
    }

    public interface GetDesiredCategoriesCallback extends DefaultCallback<List<String>>{}

}

