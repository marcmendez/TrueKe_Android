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
 * Created by marc on 27/04/17.
 */

public class GetMatchMakingListUseCase extends BaseUseCase<List<Product>> implements Interactor<Integer, List<Product> > {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private MatchMakingCallback callback;

    private int prodID;

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
    public GetMatchMakingListUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<Product>>> void execute(Integer prodID, R defaultCallback) {
        this.callback = ((MatchMakingCallback) defaultCallback);
        this.prodID= prodID;
        executor.execute(this);

    }
    @Override
    public void run() {
        repository.getMatchmakingProducts(prodID, dataCallback);
    }



    public interface MatchMakingCallback extends DefaultCallback<List<Product>>{}

}
