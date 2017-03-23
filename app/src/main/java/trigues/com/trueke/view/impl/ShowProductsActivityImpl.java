package trigues.com.trueke.view.impl;

import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ShowProductsAdapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Marc on 22/03/2017.
 */

public class ShowProductsActivityImpl extends MenuActivityImpl {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);
        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ShowProductsAdapter();
        recyclerView.setAdapter(adapter);
    }

}

