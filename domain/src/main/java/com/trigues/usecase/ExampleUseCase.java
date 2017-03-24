package com.trigues.usecase;

/**
 * Created by mbaque on 24/03/2017.
 */

public class ExampleUseCase {
//    private final RepositoryInterface repository;
//    private final ThreadExecutor executor;
//    private MostPopularUseCase.GetMostPopularListCallback callback;
//
//    RepositoryInterface.MediaListCallback dataCallback = new RepositoryInterface.MediaListCallback() {
//        @Override
//        public void onSuccess(MediaList returnParam) {
//            notifyOnSuccess(returnParam, callback);
//        }
//
//        @Override
//        public void onError(ErrorBundle errorBundle) {
//            notifyOnError(errorBundle, callback);
//        }
//    };
//
//    private int page;
//
//    @Inject
//    public MostPopularUseCase(PostExecutionThread postExecutionThread, ThreadExecutor executor, RepositoryInterface repository) {
//        super(postExecutionThread);
//        this.repository = repository;
//        this.executor = executor;
//    }
//
//    @Override
//    public <R extends DefaultCallback<MediaList>> void execute(Integer page, R defaultCallback) {
//        this.callback = ((MostPopularUseCase.GetMostPopularListCallback) defaultCallback);
//        this.page = page;
//        executor.execute(this);
//    }
//
//    @Override
//    public void run() {
//        repository.getMostPopular(page, dataCallback);
//    }
//
//    public interface GetMostPopularListCallback extends DefaultCallback<MediaList>{}
}
