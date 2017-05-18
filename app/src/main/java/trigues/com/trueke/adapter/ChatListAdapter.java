package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trigues.entity.Chat;
import com.trigues.entity.ChatInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 03/05/2017.
 */

public abstract class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context context;
    private List<ChatInfo> chatList;

    public ChatListAdapter(Context context, List<ChatInfo> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.avatarImageView.setImageResource(R.mipmap.avatar_icon);
        holder.titleTextView.setText(chatList.get(position).getProductID2());
        holder.lastMessage.setText(chatList.get(position).getChatID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChatClick(chatList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public abstract void onChatClick(ChatInfo chat);

    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        @BindView(R.id.chat_list_avatar)
        ImageView avatarImageView;

        @BindView(R.id.chat_list_name)
        TextView titleTextView;

        @BindView(R.id.chat_last_message)
        TextView lastMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            ButterKnife.bind(this, itemView);
        }
    }
}
