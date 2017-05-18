package trigues.com.data.entity;

/**
 * Created by Eduard on 16/05/2017.
 */

public class ReportProductDTO {
    private Integer user_id;
    private Integer product_id;

    public ReportProductDTO(Integer user_id, Integer product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(Integer id) {
        this.user_id = id;
    }

    public Integer getProductId() {
        return product_id;
    }

    public void setProductId(Integer id) {
        this.product_id = id;
    }

}

