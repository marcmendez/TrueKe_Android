package trigues.com.data.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.trigues.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alba on 26/04/2017.
 */

public class ProductDTO {
    @SerializedName("id")
    int id;
    @SerializedName("user_id")
    int user_id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("category")
    String category;
    @SerializedName("min_price")
    int min_price;
    @SerializedName("max_price")
    int max_price;


    private Product product;

    public ProductDTO(Product p) {
        int id= p.getId();
        this.user_id = p.getUserId();
        this.title = p.getTitle();
        this.description = p.getDescription();
        this.category = p.getProductCategory();
        this.min_price = p.getMinPrice();
        this.max_price = p.getMaxPrice();
    }

    public static Product changeType(ProductDTO p) {
        List<String> buida = new ArrayList<>();
        Product p2 = new Product(0, p.getUserId(),p.getTitle(), p.getDescription(), buida,p.getProductCategory(), buida, p.getMinPrice(), p.getMaxPrice());
        return p2;
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


    public int getMinPrice() {
        return min_price;
    }

    public int getMaxPrice() {
        return max_price;
    }
}
