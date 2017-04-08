package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.trigues.entity.Product;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.DesiredCategoriesAdapter;
import trigues.com.trueke.adapter.ImageViewPageAdapter;
import trigues.com.trueke.presenter.UserProductDetailsPresenter;

/**
 * Created by mbaque on 07/04/2017.
 */

public class MatchmakingDetailsFragImpl extends Fragment {

    @BindView(R.id.matchmaking_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.matchmaking_toolbar)
    Toolbar toolbar;

    @BindView(R.id.matchmaking_dots_layout)
    LinearLayout dotsLayout;

    @BindView(R.id.matchmaking_category_list)
    RecyclerView categoriesRecyclerview;

    @Inject
    UserProductDetailsPresenter presenter;

    ImageViewPageAdapter viewPageAdapter;
    int numImages;
    ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matchmaking_product_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String productJson = getArguments().getString("product");

        Gson gson = new Gson();
        Product product = gson.fromJson(productJson, Product.class);

        setUpProductDetails();

        setUpViewPager(product.getImages());

        setUpDotCounter();

        setUpDesiredCategoriesList(product.getDesiredCategories());
    }

    private void setUpProductDetails() {

    }

    private void setUpDesiredCategoriesList(List<String> desiredCategories) {
        this.categoriesRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        this.categoriesRecyclerview.setAdapter(new DesiredCategoriesAdapter(getContext(), desiredCategories) {
            @Override
            public void onCategoryDeleteButtonClick(String category) {
                presenter.onCategoryDeleteButtonClick(category);
            }
        });
    }

    private void setUpViewPager(List<String> images) {
        //TODO: Set info to views
        this.viewPageAdapter = new ImageViewPageAdapter(getContext(), images);
        viewPager.setAdapter(viewPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < numImages; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.user_product_details_dot_not_selected));
                }

                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.user_product_details_dot_selected));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpDotCounter() {
        numImages = viewPageAdapter.getCount();
        dots = new ImageView[numImages];

        for (int i = 0; i < numImages; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.user_product_details_dot_not_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            dotsLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.user_product_details_dot_selected));
    }


}
