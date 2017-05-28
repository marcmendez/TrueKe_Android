package trigues.com.trueke.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatInfo;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;
import com.trigues.entity.Product;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import trigues.com.data.utils.MessageJsonParser;
import trigues.com.trueke.R;
import trigues.com.trueke.view.impl.ChatListActivityImpl;

/**
 * Created by mbaque on 15/05/2017.
 */

public class ChatService extends Service {

    private static String TAG = "ChatService";

    private static boolean notify = true;
    private static List<Integer> productList = new ArrayList<>();

    private static List<String> chats;
    private Map<String, ChildEventListener> listeners;
    private DatabaseReference database;

    public static void setChats(List<ChatInfo> chats){
        List<String> string = new ArrayList<>();
        for(ChatInfo chat : chats){
            string.add(String.valueOf(chat.getId()));
        }
        ChatService.chats = string;
    }

    public static void setProductList(List<Product> productList){
        List<Integer> products = new ArrayList<>();
        for(Product product : productList){
            products.add(product.getId());
        }
        ChatService.productList = products;
    }

    public static void isNotifying(boolean notify){
        ChatService.notify = notify;
    }

    public static void addChat(String chat){
        if(ChatService.chats == null){
            ChatService.chats = new ArrayList<>();
        }
        chats.add(chat);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance().getReference();

        listeners = new HashMap<>();

        if(chats == null) chats = new ArrayList<>();

        updateChats();

        updateListeners();
    }

    private void updateChats(){
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(String chat : chats){
                    if(chat.equals(dataSnapshot.getKey())){
                        return;
                    }
                }
                for(Integer product : productList){
                    if(dataSnapshot.getKey().contains(String.valueOf(product))){
                        chats.add(dataSnapshot.getKey());
                        setUpNewChatNotification();
                        updateListeners();
                        return;
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(String chat : chats){
                    if(chat.equals(dataSnapshot.getKey())){
                        chats.remove(dataSnapshot.getKey());
                        setUpDeleteChatNotification();
                        updateListeners();
                        return;
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateListeners(){
        for(final String chat : chats) {
            final ChildEventListener listener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage message = MessageJsonParser.parseMessage(dataSnapshot.getKey(), (HashMap<String, Object>) dataSnapshot.getValue());
                    if(!message.isRead() && !isUserMessage(message)) {
                        if (message instanceof ChatTextMessage) {
                            ChatTextMessage text = (ChatTextMessage) message;
                            setupNotification(chat, text.getMessage(), text.getMessage());
                        } else if (message instanceof ChatImage) {
                            ChatImage image = (ChatImage) message;
                            setupImageNotification(chat, image.getEncodedImage());
                        } else if (message instanceof ChatLocation) {
                            ChatLocation location = (ChatLocation) message;
                            final String coordinates = Float.toString(location.getLatitude()) + "," + Float.toString(location.getLongitude());
                            String mapUrl = "http://maps.google.com/maps/api/staticmap?center=" + coordinates + "&zoom=13&size=500x300&markers=" + coordinates;

                            Target target = new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    setupImageNotification(chat, bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            };

                            Picasso.with(getApplicationContext()).load(mapUrl).into(target);
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            if(listeners.get(chat) != null) {
                database.child(chat).removeEventListener(listeners.get(chat));
            }

            database.child(chat).addChildEventListener(listener);

            listeners.put(chat, listener);
        }
    }

    private boolean isUserMessage(ChatMessage message) {
        for(Integer product : productList){
            if(product == message.getFromUserId()){
                return true;
            }
        }
        return false;
    }

    private void setupNotification(String chatId, String title, String message){
        if(notify) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int icon = R.mipmap.ic_k;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(icon);
            builder.setContentTitle(title);
            builder.setContentText(message);
            Context context = getApplicationContext();

            Intent notificationIntent = new Intent(context, ChatListActivityImpl.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
            int id = Integer.decode("0x" + String.format("%040x", new BigInteger(1, chatId.getBytes())));
            mNotificationManager.notify(id, builder.build());
        }
    }

    private void setupImageNotification(String chatId, String image){
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        setupImageNotification(chatId, bitmap);
    }

    private void setupImageNotification(String chatId, Bitmap bitmap){
        if(notify) {
            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.bigPicture(bitmap);

            Intent notificationIntent = new Intent(getApplicationContext(), ChatListActivityImpl.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

            String emoji = new String(Character.toChars(0x1F4F7));

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_k)
                    .setContentTitle(emoji + " Imagen")
                    .setContentIntent(contentIntent)
                    .setStyle(notiStyle);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int id = Integer.decode("0x" + String.format("%040x", new BigInteger(1, chatId.getBytes())));
            mNotificationManager.notify(id, notificationBuilder.build());
        }
    }

    private void setUpNewChatNotification(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.mipmap.ic_k;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(icon);
        builder.setContentTitle("Nuevo match");
        builder.setContentText("Hay un match con uno de tus productos");
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, ChatListActivityImpl.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(new Random().nextInt(), builder.build());
    }

    private void setUpDeleteChatNotification(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.mipmap.ic_k;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(icon);
        builder.setContentTitle("Chat eliminado");
        builder.setContentText("Se ha eliminado uno de tus chats");
        Context context = getApplicationContext();

        Intent notificationIntent = new Intent(context, ChatListActivityImpl.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        mNotificationManager.notify(new Random().nextInt(), builder.build());
    }
}
