package trigues.com.trueke.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trigues.entity.Payment;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.UserProfilePaymentMethodsAdapter;
import trigues.com.trueke.view.impl.UserProfileActivityImpl;

/**
 * Created by mbaque on 19/04/2017.
 */

public class UserProfilePaymentMethodsFragImpl extends Fragment{

    @BindView(R.id.user_profile_payment_methods_recyclerview)
    RecyclerView userPaymentMethodsRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Payment> userPayments;

    UserProfileActivityImpl activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Payment>>(){}.getType();
        userPayments = gson.fromJson(getArguments().getString("payment_methods"), listType);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (UserProfileActivityImpl) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_payment_methods, container, false);

        LayoutInflater.from(getContext()).inflate(R.layout.toolbar_core, (FrameLayout) view.findViewById(R.id.user_profile_payment_toolbar));

        ButterKnife.bind(this, view);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPaymentMethodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userPaymentMethodsRecyclerView.setAdapter(new UserProfilePaymentMethodsAdapter(getContext(), userPayments) {
            @Override
            public void onPaymentDeleteClick(Payment payment) {
                activity.onPaymentMethodDeleteClick(payment);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        activity.getMenuInflater().inflate(R.menu.user_profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                activity.removeFullScreenFragment();
                return true;
            case R.id.menu_user_profile_add:
                showAddDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_payment_method, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        final EditText typeET = (EditText) view.findViewById(R.id.add_payment_type);
        final EditText numberET = (EditText) view.findViewById(R.id.add_payment_number);
        final EditText expireDateET = (EditText) view.findViewById(R.id.add_payment_expireDate);
        final EditText nameET = (EditText) view.findViewById(R.id.add_payment_name);
        final EditText addressET = (EditText) view.findViewById(R.id.add_payment_address);
        final EditText cityET = (EditText) view.findViewById(R.id.add_payment_city);
        final EditText postalCodeET = (EditText) view.findViewById(R.id.add_payment_postalcode);
        final EditText provinceET = (EditText) view.findViewById(R.id.add_payment_province);
        final EditText phoneET = (EditText) view.findViewById(R.id.add_payment_phone);

        view.findViewById(R.id.add_payment_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.add_payment_send_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.newPayment(new Payment(typeET.getText().toString(), numberET.getText().toString(), expireDateET.getText().toString(), nameET.getText().toString(), provinceET.getText().toString(), cityET.getText().toString(), Integer.parseInt(postalCodeET.getText().toString()), addressET.getText().toString(), phoneET.getText().toString()));
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
