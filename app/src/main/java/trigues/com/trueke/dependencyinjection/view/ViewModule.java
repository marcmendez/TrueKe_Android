package trigues.com.trueke.dependencyinjection.view;

import dagger.Module;
import dagger.Provides;
import trigues.com.trueke.view.LoginActivity;

/**
 * Created by mbaque on 15/03/2017.
 */

@Module
public class ViewModule {

    private LoginActivity view;

    public ViewModule(LoginActivity view) {
        this.view = view;
    }

    @Provides
    LoginActivity providesLoginView(){
        return view;
    }
}