package com.trigues.usecase;

import com.trigues.callback.DefaultCallback;
import com.trigues.executor.PostExecutionThread;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by mbaque on 15/03/2017.
 */

//TODO: Canviar "Void" per classe (entity) que ha de retornar el cas d'ús
//TODO: Canviar el primer Void de Interactor per el tipus de parametre d'entrada que es vol (Void si no n'hi ha cap)
public class LogoutUseCase extends BaseUseCase<Void> implements Interactor<Void,Void> {

    @Inject
    public LogoutUseCase(PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
    }

    @Override
    public void run() {

    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(Void param, R defaultCallback) {

    }

}
