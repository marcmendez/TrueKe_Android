package trigues.com.trueke.view.impl;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trigues.entity.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.DesiredCategoriesAdapter;
import trigues.com.trueke.adapter.ImageViewPageAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.UserProductDetailsPresenter;
import trigues.com.trueke.view.UserProductDetailsActivity;
import trigues.com.trueke.view.fragment.ProductDetailAddCategoryFragImpl;

/**
 * Created by mbaque on 23/03/2017.
 */

public class UserProductDetailsActivityImpl extends BaseActivityImpl implements UserProductDetailsActivity {

    @BindView(R.id.product_detail_title)
    TextView title;

    @BindView(R.id.product_detail_description)
    TextView description;

    @BindView(R.id.product_detail_price)
    TextView price;

    @BindView(R.id.product_detail_category)
    TextView category;

    @BindView(R.id.product_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.product_detail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.product_detail_dots_layout)
    LinearLayout dotsLayout;

    @BindView(R.id.product_detail_category_list)
    RecyclerView categoriesRecyclerview;

    @BindView(R.id.product_detail_add_category)
    View addCategoryButton;

    @Inject
    UserProductDetailsPresenter presenter;

    ImageViewPageAdapter viewPageAdapter;
    int numImages;
    ImageView[] dots;

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
        Gson gson = new Gson();
        Product p = gson.fromJson(getIntent().getStringExtra("product"),Product.class);
        onDetailsRetrieved(p);

        setUpBackActionBar(toolbar);


    }

    @Override
    public void onDetailsRetrieved(Product returnParam) {
        //Fake mentre no hi hagi imatges

        List<String> fakeList = new ArrayList<>();
        fakeList.add("https://photos6.spartoo.es/photos/231/231523/231523_350_A.jpg");

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCategoryDialog();
            }
        });

        setUpViewPager(fakeList);

        setUpProductDetails(returnParam);

        setUpDotCounter();

        //setUpDesiredCategoriesList(returnParam.getDesiredCategories());
    }

    private void showAddCategoryDialog() {
        addFullScreenFragment(new ProductDetailAddCategoryFragImpl());
    }

    private void setUpProductDetails(Product product) {
        title.setText(product.getTitle());
        description.setText(product.getDescription());
        String priceText = product.getMinPrice() + " - " + product.getMaxPrice() + "€";
        price.setText(priceText);
        category.setText(product.getProductCategory());
    }

    private void setUpDesiredCategoriesList(List<String> desiredCategories) {
        this.categoriesRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        this.categoriesRecyclerview.setAdapter(new DesiredCategoriesAdapter(this, desiredCategories) {
            @Override
            public void onCategoryDeleteButtonClick(String category) {
                presenter.onCategoryDeleteButtonClick(category);
            }
        });
    }

    private void setUpViewPager(List<String> images) {
        //TODO: Set info to views
        this.viewPageAdapter = new ImageViewPageAdapter(this, images);
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
            dots[i] = new ImageView(this);
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

    public void addProductCategory(String category){
        presenter.addProductCategory(category);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_product_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.menu_delete_product){
            //TODO: Delete product
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("¿Está seguro de que desea eliminar este elemento de forma permanente?")
                    .setTitle("Confirmación")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i("Dialogos", "Confirmacion Aceptada.");

                            Gson gson = new Gson();
                            Product p = gson.fromJson(getIntent().getStringExtra("product"),Product.class);
                            presenter.deleteProduct(p.getId());

                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i("Dialogos", "Confirmacion Cancelada.");
                            dialog.cancel();
                        }
                    });

            builder.create().show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void goToShowProductList(){
        startActivity(new Intent(this, UserProductsListActivityImpl.class));
        finish();
    }
}