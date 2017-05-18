package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trigues.entity.Chat;
import com.trigues.entity.ChatMessage;

import java.lang.reflect.Type;
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
import trigues.com.trueke.presenter.ChatPresenter;
import trigues.com.trueke.view.ChatListActivity;
import trigues.com.trueke.view.fragment.ChatFragImpl;

/**
 * Created by mbaque on 03/05/2017.
 */

public class ChatListActivityImpl extends MenuActivityImpl implements ChatListActivity {

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

        initRecyclerView();
    }

    private void initRecyclerView() {
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Type listType = new TypeToken<ArrayList<Chat>>(){}.getType();

        String chatListJson = "[\n" +
                "  {\n" +
                "    \"id\" : \"1\",\n" +
                "    \"user\" : \"Marc Mandez\",\n" +
                "    \"lastMessage\" : \"Hoooliis\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\" : \"1\",\n" +
                "    \"user\" : \"Albert Valpou\",\n" +
                "    \"lastMessage\" : \"Ets un hater\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\" : \"1\",\n" +
                "    \"user\" : \"Jordi Estapa\",\n" +
                "    \"lastMessage\" : \"<3\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\" : \"1\",\n" +
                "    \"user\" : \"Marc Baqua\",\n" +
                "    \"lastMessage\" : \"Excel·lent\"\n" +
                "  }\n" +
                "\n" +
                "]";

        List<Chat> chatList = new Gson().fromJson(chatListJson, listType);

        chatListRecyclerView.setAdapter(new ChatListAdapter(this, chatList) {
            @Override
            public void onChatClick(Chat chat) {
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
