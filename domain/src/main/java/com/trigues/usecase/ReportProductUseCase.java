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
 * Created by Eduard on 16/05/2017.
 */

public class ReportProductUseCase extends BaseUseCase<Void> implements Interactor<Integer[], Void > {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private ReportProductCallback callback;
    private Integer[] userProdID = new Integer[2];

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Void reportVoid) {
            notifyOnSuccess(reportVoid, callback);
        }
    };

    @Inject
    public ReportProductUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(Integer[] userProdID, R defaultCallback) {
        this.callback = ((ReportProductCallback) defaultCallback);
        this.userProdID[0] = userProdID[0];
        this.userProdID[1] = userProdID[1];

        executor.execute(this);

    }
    @Override
    public void run() {
        repository.reportProduct(userProdID, dataCallback);

    }

    public interface ReportProductCallback extends DefaultCallback<Void>{}


}