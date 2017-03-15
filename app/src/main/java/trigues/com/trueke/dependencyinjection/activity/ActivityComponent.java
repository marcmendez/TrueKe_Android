package trigues.com.trueke.dependencyinjection.activity;

import dagger.Subcomponent;
import trigues.com.data.dependencyinjection.scope.PerActivity;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.view.impl.LoginActivityImpl;

/**
 * Created by mbaque on 15/03/2017.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class, ViewModule.class})
public interface ActivityComponent {


    void inject(LoginActivityImpl activity);
}