package trigues.com.data.entity;

import android.text.TextUtils;

import com.trigues.entity.Product;

/**
 * Created by Alba on 26/04/2017.
 */

public class ProductDTO {
    int user_id;
    String title;
    String description;
    String category;
    String wants_categories;
    int min_price;
    int max_price;
    Product product;

    public ProductDTO(Product p) {
        this.user_id = p.getUserId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        this.category = p.getProductCategory();
        this.wants_categories = TextUtils.join("-", p.getDesiredCategories());
        this.min_price = p.getMinPrice();
        this.max_price = p.getMaxPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getUserId() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

   /* public String getImages() {
        return images;
    }*/

    public String getProductCategory() {
        return category;
    }

    public String getDesiredCategories() {
        return wants_categories;
    }

    public int getMinPrice() {
        return min_price;
    }

    public int getMaxPrice() {
        return max_price;
    }
}
