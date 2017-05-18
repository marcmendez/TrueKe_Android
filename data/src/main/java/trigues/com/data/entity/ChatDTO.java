package trigues.com.data.entity;

/**
 * Created by marc on 11/05/17.
 */

public class ChatDTO {

    int my_product;
    String title;
    int product_id1;
    int product_id2;

    public ChatDTO() {
    }

    public int getid() {
        return my_product;
    }
    public String getTitle() { return title;}
    public int getproduct_id1() {
        return product_id1;
    }
    public int getproduct_id2() { return product_id2; }

    public void setChatID(int id) { this.my_product = id;    }
    public void setTitle(String title) {this.title = title;}
    public void setproduct_id1(int product_id1) {this.product_id1 = product_id1;}
    public void setproduct_id2(int product_id2) {this.product_id2 = product_id2;}

}
