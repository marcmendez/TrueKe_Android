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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.AddProductDesiredCategoryAdapter;
import trigues.com.trueke.utils.ProductCategories;
import trigues.com.trueke.view.impl.AddProductActivityImpl;

/**
 * Created by mbaque on 26/04/2017.
 */

public class AddProductDesiredCategoryFragImpl extends Fragment {

    @BindView(R.id.add_product_desired_category_recyclerview)
    RecyclerView categoryRecyclerView;

    @BindView(R.id.add_product_desired_category_close_button)
    ImageButton backButton;

    List<String> desiredCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product_desired_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        desiredCategories = new ArrayList<>();

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddProductActivityImpl) getActivity()).onDesiredCategoryPressed(desiredCategories);
                ((AddProductActivityImpl) getActivity()).removeFullScreenFragment();
            }
        });

        AddProductDesiredCategoryAdapter adapter = new AddProductDesiredCategoryAdapter(getContext(), ProductCategories.getAllCategories()) {

            @Override
            public void onDesiredCategoryCheck(String category) {
                desiredCategories.add(category);
            }

            @Override
            public void onDesiredCategoryUnCheck(String category) {
                for(int i = 0; i<desiredCategories.size(); ++i){
                    String s = desiredCategories.get(i);
                    if(s.equals(category)){
                        desiredCategories.remove(i);
                    }
                }
            }
        };

        categoryRecyclerView.setAdapter(adapter);
    }

}
