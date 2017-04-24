package trigues.com.trueke.view.impl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trigues.entity.Payment;
import com.trigues.entity.Shipment;
import com.trigues.entity.User;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.dependencyinjection.App;
import trigues.com.trueke.dependencyinjection.activity.ActivityModule;
import trigues.com.trueke.dependencyinjection.view.ViewModule;
import trigues.com.trueke.presenter.UserInfoPresenter;
import trigues.com.trueke.view.UserProfileActivity;

/**
 * Created by mbaque on 09/04/2017.
 */

public class UserProfileActivityImpl extends MenuActivityImpl implements UserProfileActivity {

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

    @Override
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
        //presenter.showPayments();
         //presenter.showShipments();
        //newPayment();
        //changeUserProfile();

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
    }

    private void changePassword() {

    }

    private void changeUsername() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Cambiar nombre de usuario");
        alertDialogBuilder.setView(R.layout.dialog_change_username);
        alertDialogBuilder.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: Implementar cambiar nombre usuario
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

    private void changeUserProfile() {
        User user = new User("albert@elputu.com","123456");
      // phone,user,password,email,birthDate
        presenter.changeProfile(user);
    }

    private void newPayment(){
        presenter.newPayment(new Payment(-1,1,"Visa/4B/Euro6000","123456789"
                ,"1990-05-06","Sancho Panza","España","Barcelona","Barcelona",
                8029,"Carrer Diagonal","654654654"));
    }
    private void changePayment(){
        presenter.changePayment(new Payment(2,1,"Visa/4B/Euro6000","123456789"
                ,"1990-05-06","Sancho Panza","España","Barcelona","Barcelona",
                8029,"Carrer Diagonal","654654654"));
        //country,province,city,postalCode,address,name,idCard,phone
    }
    private void deletePayment(){
        int payment_id = 1;
        presenter.deletePayment(payment_id);
    }
    private void newShipment(){
        presenter.newShipment(new Shipment(-1,1,"Spain","Barcelona",
                "Barcelona",8006,"Calle Falsa 123","Pepesito",
                "654845616531","654654654"));
    }
    private void changeShipment(){
        presenter.changeShipment(new Shipment(-1,1,"Spain","Barcelona",
                "Barcelona",8006,"Calle Falsa 123","Pepesito",
                "654845616531","654654654"));
    }
    private void deleteShipment(){
        int shipment_id = 1;
        presenter.deleteShipment(shipment_id);
    }
    @Override
    public void onProfileRetrieved(User user) {
        userName.setText(user.getUser()); //em guardo els valors del user?
        Toast.makeText(getApplicationContext(),"username :"+user.getUser(),Toast.LENGTH_LONG).show();
        userEmail.setText(user.getEmail());
        userNumProducts.setText(String.valueOf(user.getProducts()));
        userNumTruekes.setText(String.valueOf(user.getTruekes()));
        userRating.setRating(user.getRating());
    }

    @Override
    public void onPaymentRetrieved(List<Payment> returnParam) {
        Toast.makeText(getApplicationContext(),"Payment number1: "+returnParam.get(0).getNumber()+ "\n" +
                "Payment number2: "+returnParam.get(1).getNumber(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShipmentRetrieved(List<Shipment> returnParam) {
        Toast.makeText(getApplicationContext(),"Shipment phone1: "+returnParam.get(0).getPhone()+ "\n" +
                "Shipment phone2: "+returnParam.get(1).getPhone(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangeProfileRetrieved(Boolean returnParam) {
        Log.i("change", "onChangeProfileRetrieved: "+returnParam);
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El perfil se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnUserDeleted(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"Tu cuenta se ha añadido correctamente :(",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNewPaymentCreated(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El nuevo método de pago se ha creado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangePaymentRetrieved(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de pago se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnPaymentDeleted(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de pago se ha borrado correctamente",Toast.LENGTH_LONG).show();

    }

    @Override
    public void OnShipmentDeleted(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de envío se ha borrado correctamente",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNewShipmentCreated(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de envío se ha añadido correctamente",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onChangeShipmentRetrieved(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El método de envío se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

}
