package trigues.com.trueke.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.squareup.picasso.Picasso;
import com.trigues.entity.Product;

import trigues.com.trueke.R;
import trigues.com.trueke.view.impl.MatchmakingActivityImpl;

/**
 * Created by mbaque on 07/04/2017.
 */

@Layout(R.layout.matchmaking_item)
public class MatchmakingCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    private Product product;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private MatchmakingActivityImpl.MatchmakingCardCallback callback;

    public MatchmakingCard(Context context, Product product, SwipePlaceHolderView swipeView, MatchmakingActivityImpl.MatchmakingCardCallback callback) {
        mContext = context;
        this.product = product;
        mSwipeView = swipeView;
        this.callback = callback;
    }

    @Resolve
    private void onResolved(){
        Picasso.with(mContext).load(product.getImages().get(0)).into(profileImageView);
        nameAgeTxt.setText(product.getTitle());
        locationNameTxt.setText(product.getProductCategory());
    }

    @SwipeIn
    private void onSwipedIn(){
        callback.onAccepted();

        //TODO: NOMÉS PER TEST
        mSwipeView.addView(this);
    }

    @SwipeOut
    private void onSwipedOut(){
        callback.onRejected();

        //TODO: NOMÉS PER TEST
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}