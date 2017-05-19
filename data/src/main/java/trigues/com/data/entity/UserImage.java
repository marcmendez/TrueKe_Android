package trigues.com.data.entity;

/**
 * Created by Albert on 18/05/2017.
 */

public class UserImage {
    private String imagePath;

    public UserImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
