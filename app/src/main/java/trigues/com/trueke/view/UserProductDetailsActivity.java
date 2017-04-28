package trigues.com.trueke.view;

import com.trigues.entity.Product;

import java.util.List;

/**
 * Created by mbaque on 24/03/2017.
 */

public interface UserProductDetailsActivity extends BaseActivity {
    void onDetailsRetrieved(Product returnParam);
    void goToShowProductList();
    void setUpDesiredCategoriesList(List<String> list);
}
