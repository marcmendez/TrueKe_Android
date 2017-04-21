package trigues.com.data.entity;

import com.trigues.entity.User;

/**
 * Created by mbaque on 21/04/2017.
 */

public class LoginDTO {

    User user;
    String token;

    public LoginDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
