package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.trigues.entity.Product;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.MatchmakingPresenter;
import trigues.com.trueke.utils.MatchmakingCard;
import trigues.com.trueke.view.MatchmakingActivity;
import trigues.com.trueke.view.fragment.MatchmakingDetailsFragImpl;

/**
 * Created by mbaque on 07/04/2017.
 */

public class MatchmakingActivityImpl extends BaseActivityImpl implements MatchmakingActivity {

    @Inject
    MatchmakingPresenter presenter;

    @BindView(R.id.matchmaking_list)
    SwipePlaceHolderView matchmakingList;

    private int currentProduct;
    private int indexProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchmaking);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        currentProduct = getIntent().getIntExtra("product_id", 0);
        indexProduct = 0;
        presenter.getMatchMakingProducts(currentProduct);

        setUpBackActionBar();
    }

    @Override
    public void onProductsRetrieved(final List<Product> returnParam) {
        matchmakingList.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));


        for(final Product product : returnParam){
            matchmakingList.addView(new MatchmakingCard(this, product, matchmakingList, new MatchmakingCardCallback() {
                @Override
                public void onAccepted() {
                    ++indexProduct;

                    Integer[] productes = new Integer[2];

                    productes[1] = product.getId();
                    productes[0] = currentProduct;

                    presenter.acceptedProduct(productes);
                }

                @Override
                public void onRejected() {
                    ++indexProduct;

                    Integer[] productes = new Integer[2];

                    productes[1] = product.getId();
                    productes[0]= currentProduct;

                    presenter.rejectedProduct(productes);

                }
            }));
        }

        findViewById(R.id.matchmaking_reject_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchmakingList.doSwipe(false);
            }
        });

        findViewById(R.id.matchmaking_detail_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchmakingDetailsFragImpl fragment = new MatchmakingDetailsFragImpl();

                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                bundle.putString("product", gson.toJson(returnParam.get(indexProduct)));
                fragment.setArguments(bundle);
                //addFullScreenFragmentWithTransition(fragment, R.anim.enter_from_bottom, R.anim.exit_to_right);
                addFullScreenFragment(fragment);
            }
        });

        findViewById(R.id.matchmaking_accept_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchmakingList.doSwipe(true);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.base_container);
                if(null == currentFragment){
                    finish();
                    return true;
                }
                else{
                    return currentFragment.onOptionsItemSelected(item);
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public interface MatchmakingCardCallback {
        void onAccepted();
        void onRejected();
    }

    public void onReportPressed(Integer[] userProdID) {
        presenter.report(userProdID);
    }
}
