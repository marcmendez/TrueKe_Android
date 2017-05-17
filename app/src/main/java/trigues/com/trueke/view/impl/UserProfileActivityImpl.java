package trigues.com.trueke.view.impl;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.UserInfoPresenter;
import trigues.com.trueke.utils.FormatChecker;
import trigues.com.trueke.view.UserProfileActivity;
import trigues.com.trueke.view.fragment.UserProfileAdressesFragImpl;
import trigues.com.trueke.view.fragment.UserProfilePaymentMethodsFragImpl;

/**
 * Created by mbaque on 09/04/2017.
 */

public class UserProfileActivityImpl extends MenuActivityImpl implements UserProfileActivity {

    private static final int PICTURE_TAKEN_FROM_GALLERY = 2;
    private static final int PICTURE_TAKEN_FROM_CAMERA = 1;
    private User user;
    private UserProfilePaymentMethodsFragImpl payments;
    private UserProfileAdressesFragImpl shipments;
    @Inject
    UserInfoPresenter presenter;

    @BindView(R.id.user_profile_avatar)
    ImageView userAvatar;

    @BindView(R.id.user_profile_ratingbar)
    RatingBar userRating;

    @BindView(R.id.user_profile_name)
    TextView userName;

    @BindView(R.id.user_profile_email)
    TextView userEmail;

    @BindView(R.id.user_profile_birth_date)
    TextView userBirthDate;

    @BindView(R.id.user_profile_num_products)
    TextView userNumProducts;

    @BindView(R.id.user_profile_num_truekes)
    TextView userNumTruekes;

    @BindView(R.id.user_profile_valorations)
    TextView userNumValorations;

    @BindView(R.id.user_profile_my_addresses)
    View userAdresses;

    @BindView(R.id.user_profile_my_credit_cards)
    View userCreditCards;

    @BindView(R.id.user_profile_change_password)
    View userChangePassword;

    @BindView(R.id.user_profile_change_email)
    View userChangeUsername;
    private List<Payment> userPaymentMethods;
    private List<Shipment> userShipments;
    private String cambio;
    private String nPath;


    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ((App) getApplication())
                .getComponent()
                .plus(new ActivityModule(this),
                        new ViewModule(this))
                .inject(this);
        ButterKnife.bind(this);
        presenter.showProfile();

        userAvatar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ChangeProfileImage();
            }
        });
        userChangeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });

        userChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        userAdresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showShipments();
            }
        });

        userCreditCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showPayments();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChangeProfileImage() {
        final CharSequence[] options ={"Hacer foto", "Abrir galeria"};
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserProfileActivityImpl.this);
        builder.setTitle("Cambiar foto de perfil");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(options[which] == "Hacer foto") {
                    getPictureFromCamera();
                }
                else if (options[which] == "Abrir galeria") {
                    getPictureFromGallery();
                }
                else dialog.dismiss();
            }
        });
        builder.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getPictureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("error","Error al crear el archivo");
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                startActivityForResult(takePictureIntent, PICTURE_TAKEN_FROM_CAMERA);
            }
        }
    }

    private void getPictureFromGallery(){
        Intent gallerypickerIntent = new Intent(Intent.ACTION_PICK);
        gallerypickerIntent.setType("image/*");
        startActivityForResult(gallerypickerIntent, PICTURE_TAKEN_FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = String.valueOf(System.currentTimeMillis()/1000);
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        nPath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICTURE_TAKEN_FROM_CAMERA && resultCode == RESULT_OK ) {
            MediaScannerConnection.scanFile(this, new String[]{nPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener (){
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned" + path + "i");
                            Log.i("ExternalStorage", "-> Uri = " + uri);
                        }
                    });
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userAvatar.setImageBitmap(imageBitmap);
            presenter.changeImage(imageBitmap);
        }
        else if (requestCode == PICTURE_TAKEN_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                nPath = getPathFromURI(selectedImageUri);
                Log.i("path", "Image Path : " + nPath);
                userAvatar.setImageURI(selectedImageUri);
            }
        }
        Toast.makeText(getApplicationContext(),
                nPath, Toast.LENGTH_LONG).show();
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private void showUserPaymentMethods() {
        payments = new UserProfilePaymentMethodsFragImpl();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        bundle.putString("payment_methods", gson.toJson(userPaymentMethods));
        payments.setArguments(bundle);
        addFullScreenFragment(payments);
    }

    private void showUserAdresses() {
        shipments = new UserProfileAdressesFragImpl();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        bundle.putString("user_adresses", gson.toJson(userShipments));
        shipments.setArguments(bundle);
        addFullScreenFragment(shipments);
    }

    private void changePassword() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambiar contraseña");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null);

        final EditText newEditText = (EditText) view.findViewById(R.id.dialog_change_password_new);
        final EditText repeatEditText = (EditText) view.findViewById(R.id.dialog_change_password_repeat);

        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String newPassword = newEditText.getText().toString();
                String repeatPassword = repeatEditText.getText().toString();
                try {
                    FormatChecker.CheckPassword(newPassword,repeatPassword);
                    presenter.changeProfile("password",newPassword);
                    cambio = "La contraseña";
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.create().show();
    }

    private void changeUsername() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Cambiar nombre de usuario");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_change_username, null);

        final EditText usernameEditText = (EditText) view.findViewById(R.id.dialog_change_username_new);

        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newUsername = usernameEditText.getText().toString();
                try {
                    FormatChecker.CheckName(newUsername);
                    presenter.changeProfile("username",newUsername);
                    cambio = "El nombre de usuario";
                    dialog.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.create().show();

    }


    public void newPayment(Payment payment){

        presenter.newPayment(payment);
        userPaymentMethods.add(payment);
    }
    private void changePayment(){

    }

    public void newShipment(Shipment shipment){

        presenter.newShipment(shipment);
        userShipments.add(shipment);
    }
    private void changeShipment(){
    }

    @Override
    public void onProfileRetrieved(User user) {
        this.user = user;
        userName.setText(user.getUser());
        userEmail.setText(user.getEmail());
        userBirthDate.setText(user.getBirthDate());
        userNumProducts.setText(String.valueOf(user.getProducts()));
        userNumTruekes.setText(String.valueOf(user.getTruekes()));
        if(user.getRatingsNumber()!=0)
        userRating.setRating(user.getRatingsValue()/user.getRatingsNumber());
        else userRating.setRating(0);
        userNumValorations.setText(String.valueOf(user.getRatingsNumber()));
    }

    @Override
    public void onPaymentRetrieved(List<Payment> returnParam) {
        userPaymentMethods = returnParam;
        showUserPaymentMethods();
    }

    @Override
    public void onShipmentRetrieved(List<Shipment> returnParam) {
        userShipments = returnParam;
        showUserAdresses();
    }

    @Override
    public void onChangeProfileRetrieved(Boolean returnParam) {
        if(!returnParam) {
                Toast.makeText(getApplicationContext(), cambio+" se ha actualizado correctamente", Toast.LENGTH_LONG).show();
            if (cambio=="El nombre de usuario") userName.setText(user.getUser());
        }
        else {
            Toast.makeText(getApplicationContext(), "Ha habido un error", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void OnUserDeleted(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"Tu cuenta se ha añadido correctamente :(",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdressDeleteClick(Shipment shipment) {
        Log.i("", "shipment :"+shipment);
        presenter.deleteShipment(shipment.getId());
    }

    @Override
    public void onPaymentMethodDeleteClick(Payment payment) {
        presenter.deletePayment(payment.getId());
    }

    @Override
    public void onNewPaymentCreated(Boolean returnParam) {
        if(!returnParam){
            presenter.showPayments();
            Toast.makeText(getApplicationContext(),"El nuevo método de pago se ha creado correctamente",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onChangePaymentRetrieved(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de pago se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnPaymentDeleted(Boolean returnParam) {
        if(!returnParam){
            if(payments!=null) {
                payments.updateAdapter();
            }
            Toast.makeText(getApplicationContext(),"El método de pago se ha borrado correctamente",Toast.LENGTH_LONG).show();
            }

    }

    @Override
    public void OnShipmentDeleted(Boolean returnParam) {
        if(!returnParam) {
            if(shipments!=null) {
                shipments.updateAdapter();
            }
            Toast.makeText(getApplicationContext(),"La dirección se ha borrado correctamente",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNewShipmentCreated(Boolean returnParam) {
        if(!returnParam)
        {
            presenter.showShipments();
            Toast.makeText(getApplicationContext(),"La dirección se ha añadido correctamente",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onChangeShipmentRetrieved(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de envío se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

}
