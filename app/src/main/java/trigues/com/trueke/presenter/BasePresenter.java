package trigues.com.trueke.presenter;

import javax.inject.Inject;

import trigues.com.trueke.view.BaseActivity;

/**
 * Created by mbaque on 18/03/2017.
 */

public class BasePresenter {

    private BaseActivity view;

    @Inject
    public BasePresenter(BaseActivity view) {
        this.view = view;
    }
}
