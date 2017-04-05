package trigues.com.trueke.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.utils.FormatChecker;
import trigues.com.trueke.view.impl.LoginActivityImpl;


public class RegisterFragImpl extends Fragment implements View.OnClickListener {

    LoginActivityImpl activity;

    @BindView(R.id.edit_nombre)
    EditText e_nombre;

    @BindView(R.id.edit_apellidos)
    EditText e_apellidos;

    @BindView(R.id.edit_contraseña)
    EditText e_contraseña;

    @BindView(R.id.edit_repcontraseña)
    EditText e_repcontraseña;

    @BindView(R.id.edit_telefono)
    EditText e_telefono;

    @BindView(R.id.edit_mail)
    EditText e_mail;

    @BindView(R.id.edit_dia)
    EditText e_dia;

    @BindView(R.id.edit_mes)
    EditText e_mes;

    @BindView(R.id.edit_año)
    EditText e_año;

    @BindView(R.id.register_button)
    Button register;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        this.activity = (LoginActivityImpl) getActivity();

        LayoutInflater.from(activity).inflate(R.layout.toolbar_core, (FrameLayout) view.findViewById(R.id.register_toolbar));

        ButterKnife.bind(this, view);

        register.setOnClickListener(this);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_button:
                String nombre = e_nombre.getText().toString();
                String apellidos = e_apellidos.getText().toString();
                String contraseña = e_contraseña.getText().toString();
                String repcontraseña = e_repcontraseña.getText().toString();
                String teléfono = e_telefono.getText().toString();
                String mail = e_mail.getText().toString();
                String año = e_año.getText().toString();
                String mes = e_mes.getText().toString();
                String dia = e_dia.getText().toString();
                String fecha = año + "-" + mes + "-" + dia;
                try {
                        FormatChecker.CheckName(nombre);
                        FormatChecker.CheckUser(nombre+" "+apellidos);
                        FormatChecker.CheckPassword(contraseña,repcontraseña);
                        FormatChecker.CheckPhone(teléfono);
                        FormatChecker.CheckEmail(mail);
                        FormatChecker.CheckDate(fecha);
                        activity.onRegisterPressed(nombre,apellidos,contraseña,teléfono,mail,fecha);

                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
        }
    }


    public void onRegisterRetrieved(Boolean error) {
        //en cas del valor del bool anem a enviar sms o no
        if(!error) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Usuari creat correctamnet", Toast.LENGTH_LONG).show();
            //falta afegir fragment dialog fet per a SMS
        }else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Error al crear usuari", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:

        }
        return super.onOptionsItemSelected(item);
    }
}
