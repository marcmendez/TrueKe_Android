package trigues.com.trueke.presenter;

import javax.inject.Inject;

import trigues.com.trueke.view.MenuActivity;

/**
 * Created by mbaque on 18/03/2017.
 */

public class MenuPresenter {

    private MenuActivity view;

    @Inject
    public MenuPresenter(MenuActivity view) {
        this.view = view;
    }
}
