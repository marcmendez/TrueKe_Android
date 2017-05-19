package trigues.com.trueke.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trigues.entity.Chat;
import com.trigues.entity.ChatImage;
import com.trigues.entity.ChatLocation;
import com.trigues.entity.ChatMessage;
import com.trigues.entity.ChatTextMessage;
import com.trigues.entity.ChatTrueke;
import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.ChatAdapter;
import trigues.com.trueke.utils.LocationProvider;
import trigues.com.trueke.view.impl.ChatListActivityImpl;

/**
 * Created by mbaque on 03/05/2017.
 */

public class ChatFragImpl extends Fragment {

    private final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int LOCATION_REQUEST=1340;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.chat_recyclerview)
    RecyclerView chatRecyclerView;

    @BindView(R.id.chat_send)
    ImageButton sendButton;

    @BindView(R.id.chat_new_message_text)
    EditText messageEditText;

    private ChatListActivityImpl activity;
    private Chat chat;
    private String nPath;
    private ChatAdapter adapter;
    private ChatTrueke paymentTrueke;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        chat = new Gson().fromJson(getArguments().getString("chat"), Chat.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (ChatListActivityImpl) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        LayoutInflater.from(getContext()).inflate(R.layout.toolbar_core, (FrameLayout) view.findViewById(R.id.chat_toolbar));

        ButterKnife.bind(this, view);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(chat.getUser());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(manager);

//        Type listType = new TypeToken<ArrayList<ChatTextMessage>>(){}.getType();
//
//        String chatJson = "[\n" +
//                "  {\n" +
//                "    \"fromUserId\" : 1,\n" +
//                "    \"message\" : \"Holisss\",\n" +
//                "    \"date\" : 10000000\n" +
//                "  },\n" +
//                "\n" +
//                "  {\n" +
//                "    \"fromUserId\" : 2,\n" +
//                "    \"message\" : \"Afofahoah\",\n" +
//                "    \"date\" : 10000001\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"fromUserId\" : 2,\n" +
//                "    \"message\" : \"ohqhofhq\",\n" +
//                "    \"date\" : 10000002\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"fromUserId\" : 1,\n" +
//                "    \"message\" : \"iqowhfouhwq\",\n" +
//                "    \"date\" : 10000003\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"fromUserId\" : 1,\n" +
//                "    \"message\" : \"oqheofbqwoef\",\n" +
//                "    \"date\" : 10000004\n" +
//                "  }\n" +
//                "]";
//
//        List<ChatMessage> chat = new Gson().fromJson(chatJson, listType);

        activity.getChatMessages("1");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int user = rand.nextInt() % 2;
                user = (user < 0) ? -user : user;
                String message = messageEditText.getText().toString();
                if(!message.equals("")) {
                    ChatTextMessage chatTextMessage = new ChatTextMessage(user, Calendar.getInstance().getTimeInMillis(), message, chat.getId());
                    activity.sendMessage(chatTextMessage);
                }

                messageEditText.setText("");
            }
        });
    }

    public void addChatMessage(ChatMessage message){
        if(adapter == null) {
            adapter = new ChatAdapter(getContext(), chatRecyclerView, 1) {
                @Override
                public void onAcceptTrueke(ChatTrueke trueke) {
                    if(trueke.getShipmentType()==1) {
                        paymentTrueke = trueke;
                        activity.GetUserPayments(); //haig de mirar que només en cas de transport
                    }
                    else trueke.setStatus(2); //acceptat
                }

                @Override
                public void onRejectTrueke(ChatTrueke trueke) {
                    trueke.setStatus(1); //rejected (queda actualitzar)
                }
            };

            chatRecyclerView.setAdapter(adapter);
        }
        adapter.addMessage(message);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                activity.removeFullScreenFragment();
                return true;
            case R.id.menu_chat_image:
                showAttachOptions();
                return true;
            case R.id.menu_chat_trueke:
                showCreateTruekeDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCreateTruekeDialog() {
        final CharSequence[] options ={"Entrega a mano", "Transporte externo"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which] == "Entrega a mano") {
                    Random rand = new Random();
                    int user = rand.nextInt() % 2;
                    user = (user < 0) ? -user : user;
                    ChatTrueke chatTrueke = new ChatTrueke(user, Calendar.getInstance().getTimeInMillis(), 0, 0, chat.getId());
                    activity.sendMessage(chatTrueke);
                    dialog.dismiss();
                }
                else if (options[which] == "Transporte externo") {
                    activity.GetUserShipments();
                    dialog.dismiss();
                }
                else dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showAttachOptions() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chat_attach_options, null);
        builder.setView(view);
        builder.setTitle("Elige una opción");

        final AlertDialog dialog = builder.create();

        view.findViewById(R.id.chat_attach_camera_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictureFromCamera();
                dialog.dismiss();

            }
        });

        view.findViewById(R.id.chat_attach_gallery_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPictureFromGallery();
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.chat_attach_location_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getLocation() {
        if(canAccessGPS()) {

            LocationProvider.requestSingleUpdate(getContext(), new LocationProvider.LocationCallback() {
                @Override
                public void onNewLocationAvailable(LocationProvider.GPSCoordinates location) {
                    Random rand = new Random();
                    int userId = rand.nextInt() % 2;
                    userId = (userId < 0) ? -userId : userId;
                    ChatLocation chatLocation = new ChatLocation(userId, Calendar.getInstance().getTimeInMillis(), location.latitude, location.longitude, chat.getId());
                    activity.sendMessage(chatLocation);
                }
            });
        }
        else {
            requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
        }
    }

    private void getPictureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("error","error while creating the File");
            }
            if (photoFile != null) {
               /*Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);   */
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                startActivityForResult(takePictureIntent, PICTURE_TAKEN_FROM_CAMERA);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = String.valueOf(System.currentTimeMillis()/1000);
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        nPath = image.getAbsolutePath();
        return image;
    }

    private void getPictureFromGallery(){
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE_TAKEN_FROM_CAMERA && resultCode == activity.RESULT_OK ) {
            MediaScannerConnection.scanFile(getContext(), new String[]{nPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener (){
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned" + path + "i");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                        }
                    });
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Random rand = new Random();
            int userId = rand.nextInt() % 2;
            userId = (userId < 0) ? -userId : userId;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            ChatImage chatImage = new ChatImage(userId, Calendar.getInstance().getTimeInMillis(), encodedImage, chat.getId());

            activity.sendMessage(chatImage);

        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                nPath = getPathFromURI(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImageUri);
                    Random rand = new Random();
                    int userId = rand.nextInt() % 2;
                    userId = (userId < 0) ? -userId : userId;

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();

                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    ChatImage chatImage = new ChatImage(userId, Calendar.getInstance().getTimeInMillis(), encodedImage, chat.getId());

                    activity.sendMessage(chatImage);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private boolean canAccessGPS(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PackageManager.PERMISSION_GRANTED==activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getLocation();
    }

    public void showPaymentMethodsDialog(final List<Payment> payments){
        final int[] pos = new int[1];
        final List<String> paymentsString = new ArrayList<>();
        for(Payment payment : payments){
            paymentsString.add(payment.getNumber());
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecciona método de pago");
        builder.setSingleChoiceItems(paymentsString.toArray(new CharSequence[paymentsString.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pos[0] =which;
            }
        })
          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                  AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                  builder2.setTitle("Estas seguro?");
                  builder2.setMessage("Seguro que quieres seleccionar " + paymentsString.get(pos[0]) + "?");
                  builder2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          paymentTrueke.setStatus(3);
                          
                          dialog.dismiss();
                      }
                  });

                  builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          // showPaymentMethodsDialog(payments);
                          dialog.dismiss();
                      }
                  });
                  builder2.show();
              }
          })
           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {

               }
           });
        if(paymentsString.size()==0) Toast.makeText(getContext(),"No tienes métodos de pago",Toast.LENGTH_SHORT).show();
       else builder.show();
    }

    public void showAdressDialog(final List<Shipment> shipments){
        final int[] pos = new int[1];
        final List<String> shipmentsString = new ArrayList<>();
        for(Shipment shipment : shipments){
            shipmentsString.add(shipment.getAddress());
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Selecciona una dirección de envio");
        builder.setSingleChoiceItems(shipmentsString.toArray(new CharSequence[shipmentsString.size()]), 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pos[0] =which;
            }
        })
         .setPositiveButton("OK", new DialogInterface.OnClickListener(){

             @Override
             public void onClick(DialogInterface dialog, int which) {
                 AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                 builder2.setTitle("Estas seguro?");
                 builder2.setMessage("Seguro que quieres seleccionar " + shipmentsString.get(pos[0]) + "?");
                 builder2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Random rand = new Random();
                         int user = rand.nextInt() % 2;
                         user = (user < 0) ? -user : user;
                         ChatTrueke chatTrueke = new ChatTrueke(user, Calendar.getInstance().getTimeInMillis(), 1, 0, chat.getId());
                         activity.sendMessage(chatTrueke);
                         dialog.dismiss();
                     }
                 });

                 builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         showAdressDialog(shipments);
                     }
                 });
                 builder2.show();
             }
         })
         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });

        if(shipmentsString.size()==0) Toast.makeText(getContext(),"No tienes direcciones d'envio",Toast.LENGTH_SHORT).show();
        builder.show();
    }
}
