package trigues.com.data.datasource.impl;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trigues.callback.FirebaseChatListener;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;
import com.trigues.entity.ChatTrueke;
import com.trigues.exception.ErrorBundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import trigues.com.data.datasource.FirebaseInterface;
import trigues.com.data.utils.MessageJsonParser;

/**
 * Created by mbaque on 06/05/2017.
 */

public class FirebaseDataSource implements FirebaseInterface {

    private DatabaseReference database;

    private ValueEventListener listener;

    @Inject
    public FirebaseDataSource() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.database = database.getReference();
        //initTestDatabase();
        //testNewMessage();

    }

    private void initTestDatabase(){
        List<ChatMessage> list = new ArrayList<>();
        list.add(new ChatTextMessage(1, Calendar.getInstance().getTimeInMillis(), "Test text message"));
        list.add(new ChatImage(2, Calendar.getInstance().getTimeInMillis(), "janfoagfeponmwqefmqwpoenfq"));
        list.add(new ChatLocation(1, Calendar.getInstance().getTimeInMillis(), 120.12F, 133.5F));
        database.child("testchat").setValue(list);
    }

    private void testNewMessage(){
        ChatTextMessage testText = new ChatTextMessage(1, Calendar.getInstance().getTimeInMillis(), "Test text message");
        ChatImage testImage = new ChatImage(2, Calendar.getInstance().getTimeInMillis(), "janfoagfeponmwqefmqwpoenfq");
        ChatLocation testLocation = new ChatLocation(1, Calendar.getInstance().getTimeInMillis(), 120.12F, 133.5F);

        newMessage("testchat", testText, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });

        newMessage("testchat", testLocation, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });

        newMessage("testchat", testImage, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });
    }

    @Override
    public void getChatMessages(String chatId, final FirebaseChatListener dataCallback) {
        this.listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataCallback.onNewMessage(MessageJsonParser.parseJson(dataSnapshot));
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                dataCallback.onError(new ErrorBundle() {
                    @Override
                    public Exception getException() {
                        return databaseError.toException();
                    }

                    @Override
                    public String getErrorMessage() {
                        return databaseError.getMessage();
                    }
                });
            }
        };

        database.child(chatId).addValueEventListener(listener);
    }

    @Override
    public void newMessage(final String chatId, final ChatMessage message, final FirebaseVoidCallback dataCallback) {
        database.child(chatId).push().setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull final Task<Void> task) {
                if (task.isSuccessful()) {
                    dataCallback.onSuccess(null);
                } else {
                    dataCallback.onError(new ErrorBundle() {
                        @Override
                        public Exception getException() {
                            return task.getException();
                        }

                        @Override
                        public String getErrorMessage() {
                            return task.getException().getMessage();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void acceptTrueke(String chatId, ChatTrueke trueke, FirebaseVoidCallback dataCallback) {
        database.child(chatId).child(trueke.getTruekeId()).child("status").setValue(2);
    }

    @Override
    public void rejectTrueke(String chatId, ChatTrueke trueke, FirebaseVoidCallback dataCallback) {
        database.child(chatId).child(trueke.getTruekeId()).child("status").setValue(1);
    }

    @Override
    public void removeListeners(String chatId) {
        database.child(chatId).removeEventListener(listener);
    }


}
