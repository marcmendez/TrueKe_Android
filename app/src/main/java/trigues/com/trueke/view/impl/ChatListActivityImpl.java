package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatMessage;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ChatListAdapter;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.GetChatsPresenter;
import trigues.com.trueke.presenter.ChatPresenter;
import trigues.com.trueke.view.ChatListActivity;
import trigues.com.trueke.view.fragment.ChatFragImpl;

/**
 * Created by mbaque on 03/05/2017.
 */

public class ChatListActivityImpl extends MenuActivityImpl implements ChatListActivity {
    @Inject
    GetChatsPresenter presenterChatsuser;

    @BindView(R.id.chat_list_recyclerview)
    RecyclerView chatListRecyclerView;

    @Inject
    ChatPresenter presenter;
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
        presenterChatsuser.getChats();

    }

    public void initChatList(List<ChatInfo> chatinf) {
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


}
