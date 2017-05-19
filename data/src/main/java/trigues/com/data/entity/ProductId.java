package trigues.com.data.entity;

import com.trigues.entity.Product;

/**
 * Created by Alba on 12/05/2017.
 */

public class ProductId {
    //Integer id;
    Product product;

    public ProductId() {
    }

    public Integer getProductId() {
        return product.getId();
    }

    public void setProductId(Product product) {this.product = product;}
}
