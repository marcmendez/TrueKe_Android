package com.trigues.usecase;

import com.trigues.callback.DefaultCallback;
import com.trigues.executor.PostExecutionThread;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Marc on 22/03/2017.
 */



    public class ShowProductsUseCase extends BaseUseCase<Void> implements Interactor<Void> {

        @Inject
        public  ShowProductsUseCase(PostExecutionThread postExecutionThread) {
            super(postExecutionThread);
        }

        @Override
        public void run() {

        }

        @Override
        public <R extends DefaultCallback<Void>> void execute(R defaultCallback) {

        }
    }
