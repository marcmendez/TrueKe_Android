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
 * Created by mbaque on 05/06/2017.
 */

public class DeleteChatUseCase extends BaseUseCase<Void> implements Interactor<String,Void> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private DeleteChatUseCase.VoidCallback callback;

    private String chatId;

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(Void returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public DeleteChatUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(String chatId, R defaultCallback) {
        this.callback = ((DeleteChatUseCase.VoidCallback) defaultCallback);
        this.chatId = chatId;
        executor.execute(this);

    }
    @Override
    public void run() {
        repository.deleteChat(chatId, dataCallback);
    }


    public interface VoidCallback extends DefaultCallback<Void> {}
}
