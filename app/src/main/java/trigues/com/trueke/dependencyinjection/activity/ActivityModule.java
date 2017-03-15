package trigues.com.trueke.dependencyinjection.activity;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import trigues.com.data.dependencyinjection.qualifier.ForActivity;
import trigues.com.data.dependencyinjection.scope.PerActivity;
import trigues.com.trueke.view.impl.LoginActivityImpl;

/**
 * Created by mbaque on 15/03/2017.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(LoginActivityImpl activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    @ForActivity
    Context providesContext(){
        return activity;
    }

}