package trigues.com.trueke.view.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import trigues.com.trueke.R;


public class RegisterFragImpl extends Fragment implements View.OnClickListener {
    LoginActivityImpl activity;
    EditText e_nombre,e_apellidos,e_contraseña,e_telefono,e_mail,e_fecha;
    Button register;
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_register);
        e_nombre = (EditText) view.findViewById(R.id.edit_nombre);
        e_apellidos = (EditText) view.findViewById(R.id.edit_apellidos);
        e_contraseña = (EditText) view.findViewById(R.id.edit_contraseña);
        e_telefono= (EditText) view.findViewById(R.id.edit_telefono);
        e_mail = (EditText) view.findViewById(R.id.edit_mail);
        e_fecha = (EditText) view.findViewById(R.id.edit_fecha);
        register = (Button) view.findViewById(R.id.register_button);
        register.setOnClickListener(this);
        this.activity = (LoginActivityImpl) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
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
                String teléfono = e_telefono.getText().toString();
                String mail = e_mail.getText().toString();
                String fecha = e_fecha.getText().toString();
                try {
                        FormatChecker.CheckPassword(contraseña);
                        FormatChecker.CheckEmail(mail);
                        FormatChecker.CheckPhone(teléfono);
                        FormatChecker.CheckUser(nombre); //apellidos?
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

}
