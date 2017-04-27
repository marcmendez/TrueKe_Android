package trigues.com.trueke.view;

import com.trigues.entity.Product;

/**
 * Created by mbaque on 24/03/2017.
 */

public interface UserProductDetailsActivity extends BaseActivity {
    void onDetailsRetrieved(Product returnParam);
    void goToShowProductList();
}
