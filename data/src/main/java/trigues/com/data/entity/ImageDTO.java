package trigues.com.data.entity;

/**
 * Created by Alba on 12/05/2017.
 */

public class ImageDTO {
    int product_id;
    String image_md5;

    public ImageDTO(int product_id, String image_md5) {
        this.product_id = product_id;
        this.image_md5 = image_md5;
    }
}
