package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.ChatMessage;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by mbaque on 17/05/2017.
 */

public class SendChatMessageUseCase extends BaseUseCase<Void> implements Interactor<ChatMessage, Void>{

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private SendChatMessageCallback callback;

    private ChatMessage message;

    @Inject
    public SendChatMessageUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor,
                                  RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    RepositoryInterface.VoidCallback dataCallback = new RepositoryInterface.VoidCallback(){

        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle,callback);
        }

        @Override
        public void onSuccess(Void returnParam) {
            notifyOnSuccess(returnParam,callback);
        }
    };


    @Override
    public void run() {
        repository.sendChatMessage(message, dataCallback);
    }

    @Override
    public <R extends DefaultCallback<Void>> void execute(ChatMessage param, R defaultCallback) {
        this.callback = ((SendChatMessageCallback) defaultCallback);
        this.message = param;
        executor.execute(this);
    }


    public interface SendChatMessageCallback extends DefaultCallback<Void>{}

}
