package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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

/**
 * Created by mbaque on 07/04/2017.
 */

public class MatchmakingActivityImpl extends MenuActivityImpl implements MatchmakingActivity {

    @Inject
    MatchmakingPresenter presenter;

    @BindView(R.id.matchmaking_list)
    SwipePlaceHolderView matchmakingList;

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

        presenter.getTestProducts();
    }

    @Override
    public void onProductsRetrieved(List<Product> returnParam) {
        matchmakingList.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f));

        for(Product product : returnParam){
            matchmakingList.addView(new MatchmakingCard(this, product, matchmakingList, new MatchmakingCardCallback() {
                @Override
                public void onAccepted() {
                    //TODO: Implementar
                }

                @Override
                public void onRejected() {
                    //TODO: Implementar

                }
            }));
        }

        findViewById(R.id.matchmaking_reject_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchmakingList.doSwipe(false);
            }
        });

        findViewById(R.id.matchmaking_accept_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchmakingList.doSwipe(true);
            }
        });

    }

    public interface MatchmakingCardCallback {
        void onAccepted();
        void onRejected();
    }
}
