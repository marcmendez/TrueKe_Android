package com.trigues.usecase;

import com.trigues.RepositoryInterface;
import com.trigues.callback.DefaultCallback;
import com.trigues.entity.ImageOtherUserType;
import com.trigues.exception.ErrorBundle;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;
import com.trigues.interactor.BaseUseCase;
import com.trigues.interactor.Interactor;

import javax.inject.Inject;

/**
 * Created by Marc PC on 07/06/2017.
 */

public class GetImagesOtherUserUseCase extends BaseUseCase<String> implements Interactor<ImageOtherUserType,String> {
    private final RepositoryInterface repository;
    private final ThreadExecutor executor;
    private GetImagesUseCase.GetImagesCallback callback;

    private String image_md5;
    private int idUser;
    private ImageOtherUserType imageOtherUserType;

    RepositoryInterface.ImagesCallback dataCallback = new RepositoryInterface.ImagesCallback() {
        @Override
        public void onError(ErrorBundle errorBundle) {
            notifyOnError(errorBundle, callback);
        }

        @Override
        public void onSuccess(String returnParam) {
            notifyOnSuccess(returnParam, callback);
        }
    };

    @Inject
    public GetImagesOtherUserUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
        super(postExecutionThread);
        this.repository = repository;
        this.executor = executor;
    }

    @Override
    public <R extends DefaultCallback<String>> void execute(ImageOtherUserType imageOtherUserType, R defaultCallback) {
        this.callback = ((GetImagesUseCase.GetImagesCallback) defaultCallback);
        this.image_md5 = imageOtherUserType.getImagePath();
        this.idUser = imageOtherUserType.getUserid();
        executor.execute(this);
    }

    @Override
    public void run() {
        repository.getImagesOtherUser(idUser,image_md5, dataCallback);
    }

    public interface GetImagesCallback extends DefaultCallback<String>{}

}