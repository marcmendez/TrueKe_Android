package trigues.com.trueke.view;

import com.trigues.entity.Product;

import java.util.List;

/**
 * Created by mbaque on 07/04/2017.
 */

public interface MatchmakingActivity extends BaseActivity {
    void onProductsRetrieved(List<Product> returnParam);
    void noProducts();
}
