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
 * Created by Alba on 13/05/2017.
 */

public class GetImagesProductUseCase extends BaseUseCase<List<String>> implements Interactor<Integer,List<String>> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetImagesProductCallback callback;

    private int prodID;

    RepositoryInterface.GetImagesProductCallback dataCallback = new RepositoryInterface.GetImagesProductCallback() {
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
    public GetImagesProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<String>>> void execute(Integer prodID, R defaultCallback) {
        this.callback = ((GetImagesProductCallback) defaultCallback);
        this.prodID= prodID;
        executor.execute(this);

    }

    @Override
    public void run() {
        repository.getImagesProduct(prodID, dataCallback);
    }



    public interface GetImagesProductCallback extends DefaultCallback<List<String>>{}
}
