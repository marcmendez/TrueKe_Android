package trigues.com.trueke.view;

import com.trigues.entity.Product;

import java.util.List;

/**
 * Created by Marc on 22/03/2017.
 */

public interface ShowProductsActivity extends MenuActivity {
    void generateProds(List<Product> returnParam);
}
