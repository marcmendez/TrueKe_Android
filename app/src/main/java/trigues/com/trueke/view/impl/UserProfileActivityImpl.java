package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.UserProfilePresenter;
import trigues.com.trueke.view.UserProfileActivity;

/**
 * Created by mbaque on 09/04/2017.
 */

public class UserProfileActivityImpl extends MenuActivityImpl implements UserProfileActivity {

    @Inject
    UserProfilePresenter presenter;

    @BindView(R.id.user_profile_avatar)
    ImageView userAvatar;

    @BindView(R.id.user_profile_ratingbar)
    RatingBar userRating;

    @BindView(R.id.user_profile_name)
    TextView userName;

    @BindView(R.id.user_profile_email)
    TextView userEmail;

    @BindView(R.id.user_profile_num_products)
    TextView userNumProducts;

    @BindView(R.id.user_profile_num_truekes)
    TextView userNumTruekes;

    @BindView(R.id.user_profile_valorations)
    TextView userNumValorations;

    @BindView(R.id.user_profile_my_addresses)
    View userAdresses;

    @BindView(R.id.user_profile_my_credit_cards)
    View userCreditCards;

    @BindView(R.id.user_profile_change_password)
    View userChangePassword;

    @BindView(R.id.user_profile_change_email)
    View userChangeEmail;

    @BindView(R.id.user_profile_delete_account)
    View deleteAccount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);
    }
}
