package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ChatListAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.ChatPresenter;
import trigues.com.trueke.service.ChatService;
import trigues.com.trueke.presenter.ProductPresenter;
import trigues.com.trueke.view.ChatListActivity;
import trigues.com.trueke.view.fragment.ChatFragImpl;

/**
 * Created by mbaque on 03/05/2017.
 */

public class ChatListActivityImpl extends MenuActivityImpl implements ChatListActivity {
    @Inject
    ChatPresenter presenterChatsuser;

    Product ProductOtherUser;

    @Inject
    ProductPresenter productPresenter;

    @BindView(R.id.chat_list_recyclerview)
    RecyclerView chatListRecyclerView;

    @Inject
    ChatPresenter presenter;
    private ChatFragImpl fragment;

    RecyclerView.Adapter adapter;

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

        presenterChatsuser.getChats();

    }

    public void initChatList(List<ChatInfo> chatinf) {
        List<ChatInfo> realChat = new ArrayList<>();
        for (ChatInfo element : chatinf) {
            //try {
            //getProductMatched(Integer.parseInt(element.getProductMatched()));
            //TimeUnit.MILLISECONDS.sleep(100);
            //element.setProductMatched(ProductOtherUser.getTitle());
            realChat.add(element);
            //}
            //catch (InterruptedException e) {

            //}



        }
        ChatService.setChats(chatinf);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatListRecyclerView.setAdapter(new ChatListAdapter(this, chatinf) {
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

    public void getChatMessages(String id) {
        presenter.getChatMessages(id);
    }


    @Override
    public void addChatMessage(ChatMessage messages) {
        if(fragment != null) {
            fragment.addChatMessage(messages);
        }
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
    public void getProductMatched(int productID) {
        productPresenter.getProduct(productID);
    }

    public void productTitle(Product p) {
        List<String> hey = new ArrayList<>();
        ProductOtherUser = new Product(1, 1, "Correcio de tokens Database" , "holis", hey, "random", hey, 0, 0);

    }

}
