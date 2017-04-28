package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
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

public class DeleteCategoryToProductUseCase  extends BaseUseCase<Boolean> implements Interactor<List<String>,Boolean> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private BooleanCallback callback;

    private List<String> category;

    RepositoryInterface.BooleanCallback dataCallback = new RepositoryInterface.BooleanCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Boolean returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public DeleteCategoryToProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(List<String> p, R defaultCallback) {
        this.callback = ((BooleanCallback) defaultCallback);
        this.category = p;
        executor.execute(this);

    }
    @Override
    public void run() {
        repository.deleteProductCategory(category, dataCallback);
    }


    public interface BooleanCallback extends DefaultCallback<Boolean> {}
}
