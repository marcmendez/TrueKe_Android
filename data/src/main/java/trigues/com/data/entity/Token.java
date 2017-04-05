package trigues.com.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mbaque on 05/04/2017.
 */

public class Token {

    @SerializedName("Token")
    String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
