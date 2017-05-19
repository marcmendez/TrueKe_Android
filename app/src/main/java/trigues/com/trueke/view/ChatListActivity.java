package trigues.com.trueke.view;

import com.trigues.entity.ChatMessage;
import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;

import java.util.List;

/**
 * Created by mbaque on 03/05/2017.
 */

public interface ChatListActivity extends MenuActivity{

    void addChatMessage(ChatMessage messages);

    void onPaymentRetrieved(List<Payment> returnParam);

    void onShipmentRetrieved(List<Shipment> returnParam);

    void OnTruekeStatusUpdated();
}
