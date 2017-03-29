package trigues.com.trueke.dependencyinjection.activity;

import dagger.Subcomponent;
import trigues.com.trueke.dependencyinjection.scope.PerActivity;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.view.impl.BaseActivityImpl;
import trigues.com.trueke.view.impl.LoginActivityImpl;
import trigues.com.trueke.view.impl.MenuActivityImpl;
import trigues.com.trueke.view.impl.ShowProductsActivityImpl;
import trigues.com.trueke.view.impl.UserProductDetailsActivityImpl;

/**
 * Created by mbaque on 15/03/2017.
 */

@PerActivity
@Subcomponent(modules = {ActivityModule.class, ViewModule.class})
public interface ActivityComponent {

    void inject(BaseActivityImpl activity);

    void inject(MenuActivityImpl activity);

    void inject(LoginActivityImpl activity);

    void inject(ShowProductsActivityImpl activity);

    void inject(UserProductDetailsActivityImpl activity);
}