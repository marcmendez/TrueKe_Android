package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.AddProductCategoryAdapter;
import trigues.com.trueke.utils.ProductCategories;
import trigues.com.trueke.view.impl.AddProductActivityImpl;
import trigues.com.trueke.view.impl.UserProductDetailsActivityImpl;

/**
 * Created by mbaque on 27/04/2017.
 */

public class ProductDetailAddCategoryFragImpl extends Fragment {

    @BindView(R.id.add_product_category_recyclerview)
    RecyclerView categoryRecyclerView;

    @BindView(R.id.add_product_category_close_button)
    ImageButton backButton;

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddProductActivityImpl) getActivity()).removeFullScreenFragment();
            }
        });

        AddProductCategoryAdapter adapter = new AddProductCategoryAdapter(getActivity(), ProductCategories.getAllCategories()) {
            @Override
            public void onCategoryClick(String category) {
                ((UserProductDetailsActivityImpl) getActivity()).addProductCategory(category);
                ((UserProductDetailsActivityImpl) getActivity()).removeFullScreenFragment();
            }
        };

        categoryRecyclerView.setAdapter(adapter);
    }

}
