package trigues.com.trueke.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trigues.entity.ChatInfo;
import com.trigues.entity.Product;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.view.ChatListActivity;

/**
 * Created by mbaque on 03/05/2017.
 */

public abstract class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context context;
    private List<ChatInfo> chatList;
    public List<Product> product;

    @Inject
    ChatListActivity view;


    public ChatListAdapter(Context context, List<ChatInfo> chatList, List<Product> lp) {
        this.context = context;
        this.chatList = chatList;
        this.product = lp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(product.get(position).getImages()!= null) {
            byte[] decodedString = Base64.decode(product.get(position).getImages().get(0), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.avatarImageView.setImageBitmap(decodedByte);
        }

        holder.titleTextView.setText(chatList.get(position).getNameOtherUser());
        holder.lastMessage.setText(chatList.get(position).getTitle());

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
