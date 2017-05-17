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

import java.util.Calendar;

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
        testNewMessage();

    }

    private void testNewMessage(){
        ChatTextMessage testText = new ChatTextMessage("key1", 1, Calendar.getInstance().getTimeInMillis(), "Test text message");
        ChatImage testImage = new ChatImage("key2", 2, Calendar.getInstance().getTimeInMillis(), "janfoagfeponmwqefmqwpoenfq");
        ChatLocation testLocation = new ChatLocation("key3", 1, Calendar.getInstance().getTimeInMillis(), 120.12F, 133.5F);
        ChatTrueke trueke = new ChatTrueke("key4", 1, Calendar.getInstance().getTimeInMillis(), 1, 3);

        newMessage("1", testText, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });

        newMessage("1", testLocation, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });

        newMessage("1", testImage, new FirebaseVoidCallback() {
            @Override
            public void onError(ErrorBundle errorBundle) {

            }

            @Override
            public void onSuccess(Void returnParam) {

            }
        });

        newMessage("1", trueke, new FirebaseVoidCallback() {
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
                dataCallback.onNewMessage(MessageJsonParser.parseMessages(dataSnapshot));
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
    public void setTruekeStatus(int status, String chatId, String truekeId, FirebaseVoidCallback dataCallback) {
        database.child(chatId).child(truekeId).child("status").setValue(status);
    }

    @Override
    public void setMessageAsRead(String chatId, String key) {
        database.child(chatId).child(key).child("read").setValue(false);
    }

    @Override
    public void removeListeners(String chatId) {
        database.child(chatId).removeEventListener(listener);
    }


}
