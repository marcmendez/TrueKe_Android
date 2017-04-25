package trigues.com.trueke.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.trigues.entity.Shipment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import trigues.com.trueke.R;

/**
 * Created by mbaque on 19/04/2017.
 */

public abstract class UserProfileAddressesAdapter extends RecyclerView.Adapter<UserProfileAddressesAdapter.ViewHolder>  {

    private List<Shipment> addresses;
    private Context context;

    public UserProfileAddressesAdapter(Context context, List<Shipment> addresses) {
        this.context = context;
        if (addresses==null){
            addresses = new ArrayList<>();
        }
        this.addresses = addresses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_profile_adresses_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Shipment shipment = addresses.get(position);
        holder.streetTextView.setText(shipment.getAddress());

        String city = shipment.getCity() + ", " + shipment.getCountry();
        holder.cityTextView.setText(city);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdressDeleteClick(addresses.get(holder.getAdapterPosition()));
            }
        });
    }

    public abstract void onAdressDeleteClick(Shipment shipment);

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_profile_adresses_street)
        TextView streetTextView;

        @BindView(R.id.user_profile_adresses_city)
        TextView cityTextView;

        @BindView(R.id.user_profile_adresses_delete)
        ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
