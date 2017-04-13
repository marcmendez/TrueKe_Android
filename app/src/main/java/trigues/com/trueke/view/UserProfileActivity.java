package trigues.com.trueke.view;

import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

/**
 * Created by mbaque on 09/04/2017.
 */

public interface UserProfileActivity extends MenuActivity {
    void onProfileRetrieved(User user);
    void onPaymentRetrieved(Payment returnParam);
    void onShipmentRetrieved(Shipment returnParam);
}
