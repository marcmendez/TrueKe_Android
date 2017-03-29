package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.User;
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
public class LoginUseCase extends BaseUseCase<Boolean> implements Interactor<User,Boolean>{
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private LoginUseCaseCallback callback;
    private User user;
    @Inject
    public LoginUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                        RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.LoginUseCaseCallback dataCallback = new RepositoryInterface.LoginUseCaseCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(Boolean returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };


    @Override
    public void run() {
        repository.register(user,dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Boolean>> void execute(User param, R defaultCallback) {
        this.callback = ((LoginUseCaseCallback) defaultCallback);
        this.user = param;
        executor.execute(this);
    }


    public interface LoginUseCaseCallback extends DefaultCallback<Boolean>{}
}
