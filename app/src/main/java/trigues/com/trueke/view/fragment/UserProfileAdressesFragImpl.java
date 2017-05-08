package trigues.com.trueke.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.trigues.entity.Shipment;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;
import trigues.com.trueke.adapter.UserProfileAddressesAdapter;
import trigues.com.trueke.view.impl.UserProfileActivityImpl;

/**
 * Created by mbaque on 19/04/2017.
 */

public class UserProfileAdressesFragImpl extends Fragment {

    @BindView(R.id.user_profile_adresses_recyclerview)
    RecyclerView userAdressesRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Shipment> adresses;

    UserProfileActivityImpl activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Shipment>>(){}.getType();
        adresses = gson.fromJson(getArguments().getString("user_adresses"), listType);

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
        View view = inflater.inflate(R.layout.fragment_user_adresses, container, false);

        LayoutInflater.from(getContext()).inflate(R.layout.toolbar_core, (FrameLayout) view.findViewById(R.id.user_profile_adresses_toolbar));

        ButterKnife.bind(this, view);

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAdressesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdressesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),1));
        userAdressesRecyclerView.setAdapter(new UserProfileAddressesAdapter(getContext(), adresses) {
            @Override
            public void onAdressDeleteClick(final Shipment shipment) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Seguro que quiere borrar esta dirección?")
                        .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.onAdressDeleteClick(shipment);
                                adresses.remove(shipment);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create().show();
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_shipment, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        final EditText addressET = (EditText) view.findViewById(R.id.add_shipment_adress);
        final EditText postalCodeET = (EditText) view.findViewById(R.id.add_shipment_postalcode);
        final EditText cityET = (EditText) view.findViewById(R.id.add_shipment_city);
        final EditText provinceET = (EditText) view.findViewById(R.id.add_shipment_province);
        final EditText nameET = (EditText) view.findViewById(R.id.add_shipment_name);
        final EditText idCardET = (EditText) view.findViewById(R.id.add_shipment_idCard);
        final EditText phoneET = (EditText) view.findViewById(R.id.add_shipment_phone);


        view.findViewById(R.id.add_shipment_close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.add_shipment_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shipment s = new Shipment(provinceET.getText().toString(), cityET.getText().toString(), Integer.parseInt(postalCodeET.getText().toString()),
                        addressET.getText().toString(), nameET.getText().toString(), idCardET.getText().toString(), phoneET.getText().toString());
                activity.newShipment(s);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void updateAdapter() {
        userAdressesRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
