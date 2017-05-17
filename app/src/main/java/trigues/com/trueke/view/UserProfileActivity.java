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
    void onChangeProfileRetrieved(Boolean returnParam);
    void OnUserDeleted(Boolean returnParam);

    void onAdressDeleteClick(Shipment shipment);
    void onPaymentMethodDeleteClick(Payment payment);

    void onPaymentRetrieved(List<Payment> returnParam);
    void onNewPaymentCreated(Boolean returnParam);
    void onChangePaymentRetrieved(Boolean returnParam);
    void OnPaymentDeleted(Boolean returnParam);

    void OnShipmentDeleted(Boolean returnParam);
    void onNewShipmentCreated(Boolean returnParam);
    void onShipmentRetrieved(List<Shipment> returnParam);
    void onChangeShipmentRetrieved(Boolean returnParam);

    void OnProfileImageChanged(String returnParam);
}
