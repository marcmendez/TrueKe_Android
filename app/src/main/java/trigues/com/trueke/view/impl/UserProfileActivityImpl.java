package trigues.com.trueke.view.impl;

import android.content.DialogInterface;
import android.os.Bundle;
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

    private void fillShipments() {
        for (int i = 1; i <10 ; i++) {
            Log.i("fill", "fillShipments: "+i);
            newShipment(new Shipment(-1,-1,"Spain","Barcelona",
                    "Barcelona",i,"Calle Falsa 73","Pepesito",
                    "654845616531","654654654"));
        }
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
        presenter.changePayment(new Payment(2,1,"Visa/4B/Euro6000","123456789"
                ,"1990-05-06","Sancho Panza","España","Barcelona","Barcelona",
                8029,"Carrer Diagonal","654654654"));
        //country,province,city,postalCode,address,name,idCard,phone
    }

    public void newShipment(Shipment shipment){

        presenter.newShipment(shipment);
        userShipments.add(shipment);
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
