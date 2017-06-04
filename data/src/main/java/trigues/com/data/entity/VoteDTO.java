package trigues.com.data.entity;

/**
 * Created by Albert on 04/06/2017.
 */

public class VoteDTO {
    private float value;

    public VoteDTO(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
