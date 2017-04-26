package trigues.com.data.entity;

import com.trigues.entity.Product;
import com.trigues.entity.User;

import java.util.List;

/**
 * Created by marc on 21/04/17.
 */

public class UserProductsDTO {

        private List<Product> listProd;

        public UserProductsDTO() {
        }

        public List<Product> getProducts() {
            return listProd;
        }

        public void setProducts(List<Product> listProd) {
            this.listProd = listProd;
        }


}

