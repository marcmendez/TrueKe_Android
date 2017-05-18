package trigues.com.trueke.dependencyinjection.application;

import android.content.Context;

import com.trigues.RepositoryInterface;
import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import trigues.com.data.datasource.ApiInterface;
import trigues.com.data.datasource.FirebaseInterface;
import trigues.com.data.datasource.InternalStorageInterface;
import trigues.com.data.datasource.impl.ApiDataSource;
import trigues.com.data.datasource.impl.FirebaseDataSource;
import trigues.com.data.datasource.impl.InternalStorageDataSource;
import trigues.com.data.executor.JobExecutor;
import trigues.com.data.repository.AppRepository;
import trigues.com.trueke.UIThread;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.data.dependencyinjection.qualifier.ForApp;

/**
 * Created by mbaque on 15/03/2017.
 */
@Module
public class ApplicationModule {


    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    @ForApp
    public Context providesContext(){
        return app;
    }

    @Provides
    @Singleton
    public ThreadExecutor proviedesThreadExecutor(){
        return new JobExecutor();
    }

    @Provides
    @Singleton
    public PostExecutionThread providesPostExecutionThread(){
        return new UIThread();
    }

    @Provides
    @Singleton
    public ApiInterface providesApiDataSource(ApiDataSource dataSource){
        return dataSource;
    }

    @Provides
    @Singleton
    public FirebaseInterface providesFirebaseDataSource(FirebaseDataSource dataSource){
        return dataSource;
    }

    @Provides
    @Singleton
    public RepositoryInterface providesAppRepository(AppRepository repository){
        return repository;
    }

    @Provides
    @Singleton
    public InternalStorageInterface providesInternalStorage(InternalStorageDataSource dataSource){
        return dataSource;
    }
}
