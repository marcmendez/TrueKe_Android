package trigues.com.data.entity;

import com.trigues.entity.Product;

import java.util.List;

/**
 * Created by marc on 28/04/17.
 */

public class CategoryDTO {


        Integer product_id;
        String category;

        public CategoryDTO() {
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setProduct_id(int product_id) {this.product_id = product_id;}

}
