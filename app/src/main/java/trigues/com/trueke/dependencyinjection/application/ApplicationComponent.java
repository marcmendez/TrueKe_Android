package trigues.com.trueke.dependencyinjection.application;

import javax.inject.Singleton;

import dagger.Component;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityComponent;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(App application);

    ActivityComponent plus(ActivityModule activityModule, ViewModule viewModule);
}
