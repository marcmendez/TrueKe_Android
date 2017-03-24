package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.trigues.entity.Product;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ImageViewPageAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.UserProductDetailsPresenter;
import trigues.com.trueke.view.UserProductDetailsActivity;

/**
 * Created by mbaque on 23/03/2017.
 */

public class UserProductDetailsActivityImpl extends BaseActivityImpl implements UserProductDetailsActivity {

    @BindView(R.id.product_detail_viewpager)
    ViewPager viewPager;

    @Inject
    UserProductDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userproduct_detail);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        int productId = getIntent().getIntExtra("user_product_id", -1);

        presenter.getProductDetails(productId);
    }

    @Override
    public void onDetailsRetrieved(Product returnParam) {
        //TODO: Set info to views
        viewPager.setAdapter(new ImageViewPageAdapter(this, new ArrayList<String>()));
    }
}
