package trigues.com.trueke.dependencyinjection.activity;

import dagger.Subcomponent;
import trigues.com.data.dependencyinjection.scope.PerActivity;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.view.impl.BaseActivityImpl;
import trigues.com.trueke.view.impl.LoginActivityImpl;
import trigues.com.trueke.view.impl.MenuActivityImpl;

/**
 * Created by mbaque on 15/03/2017.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class, ViewModule.class})
public interface ActivityComponent {

    void inject(BaseActivityImpl activity);

    void inject(MenuActivityImpl activity);

    void inject(LoginActivityImpl activity);
}