package trigues.com.trueke.view.impl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

    private User user;
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
       // presenter.showPayments();
       // presenter.showShipments();
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

    private void showUserPaymentMethods() {
        Fragment fragment = new UserProfilePaymentMethodsFragImpl();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        bundle.putString("payment_methods", gson.toJson(userPaymentMethods));
        fragment.setArguments(bundle);
        addFullScreenFragment(fragment);
    }

    private void showUserAdresses() {
        Fragment fragment = new UserProfileAdressesFragImpl();
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        bundle.putString("user_adresses", gson.toJson(userShipments));
        fragment.setArguments(bundle);
        addFullScreenFragment(fragment);
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
                    User newuser = user;
                    newuser.setPassword(newPassword);
                    presenter.changeProfile(newuser);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

                //TODO: Implementar cambiar nombre usuario

                dialog.dismiss();

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

    public void newPayment(Payment payment){
        presenter.newPayment(payment);
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
    public void newShipment(Shipment shipment){
        presenter.newShipment(shipment);
    }
    private void changeShipment(){
        presenter.changeShipment(new Shipment(-1,1,"Spain","Barcelona",
                "Barcelona",8006,"Calle Falsa 123","Pepesito",
                "654845616531","654654654"));
    }

    @Override
    public void onProfileRetrieved(User user) {
        this.user = user;
        userName.setText(user.getUser());
        userEmail.setText(user.getEmail());
        userNumProducts.setText(String.valueOf(user.getProducts()));
        userNumTruekes.setText(String.valueOf(user.getTruekes()));
        userRating.setRating(user.getRating());
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
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"El perfil se ha actualizado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnUserDeleted(Boolean returnParam) {
        if(!returnParam)
            Toast.makeText(getApplicationContext(),"Tu cuenta se ha añadido correctamente :(",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdressDeleteClick(Shipment shipment) {
        presenter.deleteShipment(shipment.getId());
    }

    @Override
    public void onPaymentMethodDeleteClick(Payment payment) {
        presenter.deletePayment(payment.getId());
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
