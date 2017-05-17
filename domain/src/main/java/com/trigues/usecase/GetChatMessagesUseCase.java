package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.ChatMessage;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mbaque on 17/05/2017.
 */

public class GetChatMessagesUseCase extends BaseUseCase<List<ChatMessage>> implements Interactor<String, List<ChatMessage>> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetChatMessagesUseCase.GetChatMessagesListener callback;

    private String chatId;

    RepositoryInterface.ChatMessagesCallback dataCallback = new RepositoryInterface.ChatMessagesCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(List<ChatMessage> returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetChatMessagesUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<ChatMessage>>> void execute(String chatId, R defaultCallback) {
        this.callback = ((GetChatMessagesUseCase.GetChatMessagesListener) defaultCallback);
        this.chatId = chatId;
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getChatMessages(chatId, dataCallback);
    }

    public interface GetChatMessagesListener extends DefaultCallback<List<ChatMessage>>{}
}
