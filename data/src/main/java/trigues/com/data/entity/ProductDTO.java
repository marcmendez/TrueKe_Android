package trigues.com.data.entity;

import com.trigues.entity.Product;

/**
 * Created by Alba on 26/04/2017.
 */

public class ProductDTO {
    int id;
    int userId;
    String title;
    String description;
   // String images;
    String productCategory;
    String desiredCategories;
    int minPrice;
    int maxPrice;

    public ProductDTO(Product p) {
        this.id = p.getId();
        this.userId = p.getUserId();
        this.title = p.getTitle();
        this.description = p.getDescription();
      //  this.images = p.getImages();
        this.productCategory = p.getProductCategory();
        boolean first = true;
        for (String iter: p.getDesiredCategories()) {
            if(first) {
                this.desiredCategories = iter;
                first = false;
            }
            else this.desiredCategories = this.desiredCategories+ "-" + iter;
        }
        this.minPrice = p.getMinPrice();
        this.maxPrice = p.getMaxPrice();
    }
}
