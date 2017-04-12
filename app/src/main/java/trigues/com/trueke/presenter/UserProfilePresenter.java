package trigues.com.trueke.presenter;

import javax.inject.Inject;

import trigues.com.trueke.view.UserProfileActivity;

/**
 * Created by mbaque on 09/04/2017.
 */

public class UserProfilePresenter {

    private UserProfileActivity view;

    @Inject
    public UserProfilePresenter(UserProfileActivity view) {
        this.view = view;
    }
}
