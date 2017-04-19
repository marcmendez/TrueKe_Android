package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.AddProductCategoryAdapter;
import trigues.com.trueke.utils.ProductCategories;

/**
 * Created by mbaque on 19/04/2017.
 */

public class AddProductCategoryFragImpl extends Fragment {

    @BindView(R.id.add_product_category_recyclerview)
    RecyclerView categoryRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AddProductCategoryAdapter adapter = new AddProductCategoryAdapter(getActivity(), ProductCategories.getAllCategories()) {
            @Override
            public void onCategoryClick(String category) {

            }
        };

        categoryRecyclerView.setAdapter(adapter);
    }
}
