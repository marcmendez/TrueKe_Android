package trigues.com.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mbaque on 29/03/2017.
 */

public class ApiDTO<T> {

    @SerializedName("Error")
    Boolean error;

    @SerializedName("Message")
    String message;

    @SerializedName("Content")
    T content;

    public ApiDTO(Boolean error, String message, T content) {
        this.error = error;
        this.message = message;
        this.content = content;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
