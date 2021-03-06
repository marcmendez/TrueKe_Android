package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by mbaque on 15/03/2017.
 */

//TODO: Canviar "Void" per classe (entity) que ha de retornar el cas d'ús
//TODO: Canviar el primer Void de Interactor per el tipus de parametre d'entrada que es vol (Void si no n'hi ha cap)
public class LogoutUseCase extends BaseUseCase<Void> implements Interactor<Void,Void> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private LogoutUseCase.LogoutUseCaseCallback callback;

    @Inject
    public LogoutUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                        RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Void returnParam) {
            notifyOnSuccess(null, callback);
        }
    };


    @Override
    public void run() {
        repository.logout(dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(Void param, R defaultCallback) {
        this.callback = ((LogoutUseCase.LogoutUseCaseCallback) defaultCallback);
        executor.execute(this);
    }


    public interface LogoutUseCaseCallback extends DefaultCallback<Void>{}
}
