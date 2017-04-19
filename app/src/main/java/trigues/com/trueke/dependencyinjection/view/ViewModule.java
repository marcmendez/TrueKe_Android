package trigues.com.trueke.dependencyinjection.view;

import dagger.Module;
import dagger.Provides;
import trigues.com.trueke.view.AddProductActivity;
import trigues.com.trueke.view.BaseActivity;
import trigues.com.trueke.view.LoginActivity;
import trigues.com.trueke.view.MenuActivity;
import trigues.com.trueke.view.ShowProductsActivity;
import trigues.com.trueke.view.UserProductDetailsActivity;

/**
 * Created by mbaque on 15/03/2017.
 */

@Module
public class ViewModule {

    private BaseActivity view;

    public ViewModule(BaseActivity view) {
        this.view = view;
    }

    @Provides
    BaseActivity providesBaseView(){
        return view;
    }

    @Provides
    MenuActivity providesMenuView(){
        return (MenuActivity) view;
    }

    @Provides
    LoginActivity providesLoginView(){
        return (LoginActivity) view;
    }

    @Provides
    ShowProductsActivity providesShowProductsView(){
        return (ShowProductsActivity) view;
    }

    @Provides
    UserProductDetailsActivity providesUserProductDetailsView(){
        return (UserProductDetailsActivity) view;
    }

    @Provides
    AddProductActivity providesAddProductView() {
        return (AddProductActivity) view;
    }
}