package trigues.com.trueke.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

        userAdressesRecyclerView.setAdapter(new UserProfileAddressesAdapter(getContext(), adresses) {
            @Override
            public void onAdressDeleteClick(Shipment shipment) {
                activity.onAdressDeleteClick(shipment);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                activity.removeFullScreenFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
