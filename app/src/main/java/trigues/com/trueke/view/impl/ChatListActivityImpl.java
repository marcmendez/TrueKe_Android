package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.Payment;
import com.trigues.entity.Product;
import com.trigues.entity.Shipment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ChatListAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.ChatDataPresenter;
import trigues.com.trueke.presenter.ChatPresenter;
import trigues.com.trueke.service.ChatService;
import trigues.com.trueke.view.ChatListActivity;
import trigues.com.trueke.view.fragment.ChatFragImpl;

/**
 * Created by mbaque on 03/05/2017.
 */

public class ChatListActivityImpl extends MenuActivityImpl implements ChatListActivity {

    List<Product> ListProductOtherUser;
    List<ChatInfo> ListNamesChats;

    @BindView(R.id.chat_list_recyclerview)
    RecyclerView chatListRecyclerView;

    @Inject
    ChatPresenter presenter;

    @Inject
    ChatDataPresenter userInfoPresenter;
    private ChatFragImpl fragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);

        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            fragment = (ChatFragImpl) getSupportFragmentManager().getFragment(savedInstanceState, "ChatFragment");
        }

        presenter.getChats();

    }

    public void initChatList(List<ChatInfo> chatinf) {
        List<Integer> realChat = new ArrayList<>();
        ListNamesChats = chatinf;
        for (ChatInfo element : chatinf) {
            realChat.add(element.getProduct_id2());
        }
        getProductMatched(realChat);
        ChatService.setChats(chatinf);

    }

    public void getChatMessages(String id) {
        presenter.getChatMessages(id);
    }


    @Override
    public void addChatMessage(ChatMessage messages) {
        if(fragment != null) {
            fragment.addChatMessage(messages);
        }
    }

    @Override
    public void onPaymentRetrieved(List<Payment> returnParam) {
        fragment.showPaymentMethodsDialog(returnParam);
    }

    @Override
    public void onShipmentRetrieved(List<Shipment> returnParam) {
        fragment.showAdressDialog(returnParam);
    }

    @Override
    public void OnTruekeStatusUpdated() {

    }

    @Override
    public void OnTruekeCreated() {
    }

    @Override
    public void onTruekePaid() {
        fragment.setTruekePaid();
    }

    @Override
    public void onTruekeVoted() {

    }

    public void sendMessage(ChatMessage chatTextMessage) {
        presenter.sendMessage(chatTextMessage);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(fragment != null && fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ChatFragment", fragment);
        }
    }
    public void getProductMatched(List<Integer> productID) {
        presenter.getProducts(productID);
    }

    public void setproductTitle(List<Product> ProductOtherUser) {
        this.ListProductOtherUser = ProductOtherUser;
        int i = 0;
        ChatInfo ci;
        for (Product element : ProductOtherUser) {
            ci = ListNamesChats.get(i);
            ci.setTitleOtherUser(element.getTitle());
            ListNamesChats.set(i, ci);
            i++;
        }
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatListRecyclerView.setAdapter(new ChatListAdapter(this, ListNamesChats) {
            @Override
            public void onChatClick(ChatInfo chat) {
                fragment = new ChatFragImpl();
                Bundle bundle = new Bundle();
                bundle.putString("chat", new Gson().toJson(chat));
                fragment.setArguments(bundle);
                addFullScreenFragment(fragment);
            }
        });
    }


    public void setTruekeStatus(int status, String s, String truekeID){presenter.setTruekeStatus(status,s,truekeID);}

    public void GetUserShipments() {
        userInfoPresenter.showShipments();
    }

    public void GetUserPayments() {
        userInfoPresenter.showPayments();
    }

    public void createTrueke(String chatID) {
        presenter.createTrueke(chatID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ChatService.isNotifying(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ChatService.isNotifying(true);
    }

    public void PayTrueke(int my_product, String chat_id, int payment_id) {
        presenter.PayTrueke(my_product,chat_id,payment_id);
    }

    public void ValorarUsuari() {
        fragment.showValorarDialog();
    }

    public void valoraTrueke(float rating, int product_id2, String chatId, boolean isUser1, int otherProduct) {
        presenter.voteTrueke(rating,product_id2, chatId, isUser1, otherProduct);
    }
}
