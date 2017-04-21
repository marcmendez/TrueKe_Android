package trigues.com.trueke.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trigues.entity.Product;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.UserProductsRecyclerViewAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.ShowProductsPresenter;
import trigues.com.trueke.view.UserProductsListActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class UserProductsListActivityImpl extends MenuActivityImpl implements UserProductsListActivity {

    @Inject
    ShowProductsPresenter presenter;

    @BindView(R.id.user_products_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.user_products_add_product_fab)
    FloatingActionButton addProductButton;

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_products_activity);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        presenter.getUserProducts(54321);


    }

    public void generateProds(List<Product> product) {

        adapter = new UserProductsRecyclerViewAdapter(this, product) {
            @Override
            public void  onMatchMakingClick() {
                startActivity(new Intent(UserProductsListActivityImpl.this, MatchmakingActivityImpl.class));
            }
            @Override
            public void onItemClick() {
                startActivity(new Intent(UserProductsListActivityImpl.this, UserProductDetailsActivityImpl.class));
            }

        };

        recyclerView.setAdapter(adapter);
    }

}

