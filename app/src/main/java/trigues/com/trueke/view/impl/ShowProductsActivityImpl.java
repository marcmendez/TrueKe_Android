package trigues.com.trueke.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trigues.entity.Product;

import java.util.List;

import javax.inject.Inject;

import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ShowProductsAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.ShowProductsPresenter;
import trigues.com.trueke.view.ShowProductsActivity;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsActivityImpl extends MenuActivityImpl implements ShowProductsActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    @Inject
    ShowProductsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);


        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        presenter.getUserProducts(54321);


    }

    public void generateProds(List<Product> product) {

        adapter = new ShowProductsAdapter(this, product) {
            @Override
            public void onItemClick() {
                startActivity(new Intent(ShowProductsActivityImpl.this, UserProductDetailsActivityImpl.class));
            }
        };
        recyclerView.setAdapter(adapter);



    }

}

