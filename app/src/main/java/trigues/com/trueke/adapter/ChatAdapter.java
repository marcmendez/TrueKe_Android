package trigues.com.trueke.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;
import com.trigues.entity.ChatTrueke;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 04/05/2017.
 */

public abstract class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final int TEXT_FROM_CURRENT_USER = 0;
    private static final int TEXT_FROM_OTHER_USER = 1;
    private static final int IMAGE_FROM_CURRENT_USER = 2;
    private static final int IMAGE_FROM_OTHER_USER = 3;
    private static final int LOCATION_FROM_CURRENT_USER = 4;
    private static final int LOCATION_FROM_OTHER_USER = 5;
    private static final int TRUEKE_FROM_CURRENT_USER = 6;
    private static final int TRUEKE_FROM_OTHER_USER = 7;

    private Context context;
    private List<ChatMessage> messages;
    private int currentUserId;
    private RecyclerView recyclerView;

    public ChatAdapter(Context context, RecyclerView recyclerView, int currentUserId, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
        this.currentUserId = currentUserId;
        this.recyclerView = recyclerView;

        sortList();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TEXT_FROM_CURRENT_USER) {
            view = LayoutInflater.from(context).inflate(R.layout.message_from_current_user_item, parent, false);
        }
        else if(viewType == TEXT_FROM_OTHER_USER){
            view = LayoutInflater.from(context).inflate(R.layout.message_from_other_user_item, parent, false);
        }
        else if(viewType == TRUEKE_FROM_CURRENT_USER){
            view = LayoutInflater.from(context).inflate(R.layout.trueke_from_current_user_item, parent, false);
        }
        else if(viewType == TRUEKE_FROM_OTHER_USER){
            view = LayoutInflater.from(context).inflate(R.layout.trueke_from_other_user_item, parent, false);
        }
        else if(viewType == LOCATION_FROM_CURRENT_USER){
            view = LayoutInflater.from(context).inflate(R.layout.location_from_current_user, parent, false);
        }
        else if(viewType == LOCATION_FROM_OTHER_USER){
            view = LayoutInflater.from(context).inflate(R.layout.location_from_other_user, parent, false);
        }
        else if(viewType == IMAGE_FROM_CURRENT_USER){
            view = LayoutInflater.from(context).inflate(R.layout.image_from_current_user, parent, false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.image_from_other_user, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (messages.get(position) instanceof ChatTextMessage) {
            holder.message.setText(((ChatTextMessage) messages.get(position)).getMessage());
        }
        else if(messages.get(position) instanceof ChatLocation) {
            ChatLocation location = (ChatLocation) messages.get(position);
            final String coordinates = Float.toString(location.getLatitude()) + "," + Float.toString(location.getLongitude());
            String mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + coordinates + "&zoom=13&size=500x300&markers=" + coordinates;
            Picasso.with(context).load(mapUrl).into(holder.location);

            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + coordinates));
                    context.startActivity(browserIntent);
                }
            });
        }
        else if(messages.get(position) instanceof ChatTrueke){
            final ChatTrueke trueke = (ChatTrueke) messages.get(position);

            holder.typeTrueke.setText(trueke.getShipmentTypeString());
            holder.statusTrueke.setText(trueke.getStatusString());

            if (messages.get(position).getFromUserId() != currentUserId){
                if(trueke.getStatus() == 0) {
                    holder.truekeButtonsLayout.setVisibility(View.VISIBLE);
                    holder.acceptTrueke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //trueke.setStatus(2);
                            onAcceptTrueke((ChatTrueke) messages.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                            recyclerView.scrollToPosition(getItemCount() - 1);
                        }
                    });

                    holder.rejectTrueke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // trueke.setStatus(1);
                            onRejectTrueke((ChatTrueke) messages.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                            recyclerView.scrollToPosition(getItemCount() - 1);

                        }
                    });
                }
                else {
                    holder.truekeButtonsLayout.setVisibility(View.GONE);
                }
                if(trueke.getStatus()==3){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onWaitingPayment((ChatTrueke) messages.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                            recyclerView.scrollToPosition(getItemCount() - 1);
                        }
                    });
                }
                if(trueke.getStatus()==2 && trueke.getShipmentType()==1){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onWaitingAddress((ChatTrueke) messages.get(holder.getAdapterPosition()));
                            notifyDataSetChanged();
                            recyclerView.scrollToPosition(getItemCount() - 1);
                        }
                    });
                }
            }
        }
        else{
            byte[] decodedString = Base64.decode(((ChatImage) messages.get(position)).getEncodedImage(), Base64.DEFAULT);
            final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.image.setImageBitmap(decodedByte);
        }
    }

    protected abstract void onWaitingAddress(ChatTrueke trueke);

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getFromUserId() == currentUserId){
            if(messages.get(position) instanceof ChatTextMessage) {
                return TEXT_FROM_CURRENT_USER;
            }
            else if(messages.get(position) instanceof ChatLocation){
                return LOCATION_FROM_CURRENT_USER;
            }
            else if(messages.get(position) instanceof ChatTrueke){
                return TRUEKE_FROM_CURRENT_USER;
            }
            else{
                return IMAGE_FROM_CURRENT_USER;
            }
        }
        else {
            if(messages.get(position) instanceof ChatTextMessage) {
                return TEXT_FROM_OTHER_USER;
            }
            else if (messages.get(position) instanceof ChatTrueke){
                return TRUEKE_FROM_OTHER_USER;
            }
            else if(messages.get(position) instanceof ChatLocation){
                return LOCATION_FROM_OTHER_USER;
            }
            else{
                return IMAGE_FROM_OTHER_USER;
            }

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addMessage(ChatMessage message){
        messages.add(message);
        sortList();
        notifyDataSetChanged();
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    public void addMessages(List<ChatMessage> messages){
        messages.addAll(messages);
        sortList();
        notifyDataSetChanged();
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    private void sortList(){
        Log.i("sortlist", "sortList: "+messages);
        for(int i = 0; i<messages.size(); ++i){
            for(int j = 0; j<messages.size(); ++j){
                ChatMessage message1 = messages.get(i);
                ChatMessage message2 = messages.get(j);
                if(i != j && message1.getDate().equals(message2.getDate())){
                    messages.remove(j);
                }
            }
        }


        Collections.sort(this.messages, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage o1, ChatMessage o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    public abstract void onAcceptTrueke(ChatTrueke trueke);
    public abstract void onRejectTrueke(ChatTrueke trueke);
    public abstract void onWaitingPayment(ChatTrueke trueke);
    class ViewHolder extends RecyclerView.ViewHolder{

        @Nullable
        @BindView(R.id.message_text)
        TextView message;

        @Nullable
        @BindView(R.id.chat_image)
        ImageView image;

        @Nullable
        @BindView(R.id.chat_location)
        ImageView location;

        @Nullable
        @BindView(R.id.trueke_type)
        TextView typeTrueke;

        @Nullable
        @BindView(R.id.trueke_status)
        TextView statusTrueke;

        @Nullable
        @BindView(R.id.trueke_accept)
        ImageView acceptTrueke;

        @Nullable
        @BindView(R.id.trueke_reject)
        ImageView rejectTrueke;

        @Nullable
        @BindView(R.id.trueke_buttons_layout)
        View truekeButtonsLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
