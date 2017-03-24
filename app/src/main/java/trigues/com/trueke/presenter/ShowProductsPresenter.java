package trigues.com.trueke.presenter;

import javax.inject.Inject;

import trigues.com.trueke.view.ShowProductsActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsPresenter {
    private ShowProductsActivity view;


    public ShowProductsPresenter(ShowProductsActivity view) {
        this.view = view;
    }
}

