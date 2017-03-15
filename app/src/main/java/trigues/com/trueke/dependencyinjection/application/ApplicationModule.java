package trigues.com.trueke.dependencyinjection.application;

import android.content.Context;

import com.trigues.executor.PostExecutionThread;
import com.trigues.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import trigues.com.data.dependencyinjection.DataModule;
import trigues.com.data.dependencyinjection.qualifier.ForApp;
import trigues.com.data.executor.JobExecutor;
import trigues.com.trueke.UIThread;
import trigues.com.trueke.dependencyinjection.App;

/**
 * Created by mbaque on 15/03/2017.
 */
@Module(
        includes = {
                DataModule.class,
        }
)
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
}
