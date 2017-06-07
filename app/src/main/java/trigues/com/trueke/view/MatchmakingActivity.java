package trigues.com.trueke.view;

import com.trigues.entity.Product;
import com.trigues.entity.User;

import java.util.List;

/**
 * Created by mbaque on 07/04/2017.
 */

public interface MatchmakingActivity extends BaseActivity {
    void onProductsRetrieved(List<Product> returnParam);
    void noProducts();
    void OnProfileImage(String photo);
    void setInfo(Integer id, User userInfo);
    Product getCurrentProduct();
}
