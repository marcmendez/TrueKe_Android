package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.Product;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mbaque on 24/03/2017.
 */

public class GetChatsUseCase extends BaseUseCase<List<ChatInfo>> implements Interactor<Integer,List<ChatInfo>> {

    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetChatsListCallback callback;

    RepositoryInterface.ChatListCallback dataCallback = new RepositoryInterface.ChatListCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(List<ChatInfo> returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetChatsUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<List<ChatInfo>>> void execute(Integer userID, R defaultCallback) {
        this.callback = ((GetChatsListCallback) defaultCallback);
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getUserChats(dataCallback);
    }

    public interface GetChatsListCallback extends DefaultCallback<List<ChatInfo>>{}

}