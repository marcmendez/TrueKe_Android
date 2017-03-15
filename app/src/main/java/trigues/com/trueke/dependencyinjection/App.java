package trigues.com.trueke.dependencyinjection;

import android.app.Application;

import trigues.com.trueke.dependencyinjection.application.ApplicationComponent;
import trigues.com.trueke.dependencyinjection.application.ApplicationModule;
import trigues.com.trueke.dependencyinjection.application.DaggerApplicationComponent;

/**
 * Created by mbaque on 15/03/2017.
 */

public class App extends Application {

    ApplicationComponent component = null;

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();


        component.inject(this);
    }
}
