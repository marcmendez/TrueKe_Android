package trigues.com.trueke.view;

import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

/**
 * Created by mbaque on 09/04/2017.
 */

public interface UserProfileActivity extends MenuActivity {
    void onProfileRetrieved(User user);
    void onPaymentRetrieved(List<Payment> returnParam);
    void onShipmentRetrieved(List<Shipment> returnParam);
    void onChangeProfileRetrieved(Boolean returnParam);
    void OnUserDeleted(Boolean returnParam);
}
